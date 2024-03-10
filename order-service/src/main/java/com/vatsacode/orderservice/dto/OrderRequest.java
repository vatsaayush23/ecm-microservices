package com.vatsacode.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Long customerId;
    private List<OrderLineItemDto> orderLineItemDtoList;
    public Map<String, Integer> getSkuCodeToQuantityMap() {
        Map<String, Integer> skuCodeToQuantityMap = new HashMap<>();
        for (OrderLineItemDto itemDto : orderLineItemDtoList) {
            skuCodeToQuantityMap.put(itemDto.getSkuCode(), itemDto.getQuantity());
        }
        return skuCodeToQuantityMap;
    }
}
