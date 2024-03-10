package com.vatsacode.orderservice.service;

import com.vatsacode.orderservice.dto.OrderLineItemDto;
import com.vatsacode.orderservice.dto.OrderRequest;
import com.vatsacode.orderservice.model.Order;
import com.vatsacode.orderservice.model.OrderLineItem;
import com.vatsacode.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {
        Map<String, Integer> skuCodeToQuantityMap = orderRequest.getSkuCodeToQuantityMap();
        boolean allProductsInStock = checkAllProductsInStock(skuCodeToQuantityMap);
        if (!allProductsInStock) {
            log.info("All products are not in stock at the moment");
            throw new IllegalArgumentException("All products are not in stock at the moment, please try again later");
        }
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setCustomerId(orderRequest.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapDtoToModel)
                .toList();
        order.setOrderLineItemList(orderLineItemList);
        orderRepository.save(order);
        log.info("Order {} placed successfully", order.getId());
    }

    private boolean checkAllProductsInStock(Map<String, Integer> skuCodeToQuantityMap) {
        StringBuilder uriBuilder = new StringBuilder("http://localhost:8082/api/inventory/check?");
        for (Map.Entry<String, Integer> entry : skuCodeToQuantityMap.entrySet()) {
            uriBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        String uri = uriBuilder.toString();
        Boolean result = webClientBuilder.build().get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return result != null && result;
    }

    private OrderLineItem mapDtoToModel(OrderLineItemDto orderLineItemDto) {
        return OrderLineItem.builder()
                .id(orderLineItemDto.getId())
                .skuCode(orderLineItemDto.getSkuCode())
                .quantity(orderLineItemDto.getQuantity())
                .totalPrice(orderLineItemDto.getTotalPrice())
                .build();
    }
}
