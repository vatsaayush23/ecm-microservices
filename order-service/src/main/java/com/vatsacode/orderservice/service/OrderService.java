package com.vatsacode.orderservice.service;

import com.vatsacode.orderservice.dto.OrderLineItemDto;
import com.vatsacode.orderservice.dto.OrderRequest;
import com.vatsacode.orderservice.model.Order;
import com.vatsacode.orderservice.model.OrderLineItem;
import com.vatsacode.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
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
