package com.vatsacode.inventoryservice.service;

import com.vatsacode.inventoryservice.model.Inventory;
import com.vatsacode.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(Map<String, Integer> skuCodeToQuantityMap) {
        List<String> skuCodesList = new ArrayList<>(skuCodeToQuantityMap.keySet());
        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCodesList);
        if (inventoryList.isEmpty() || inventoryList.size() != skuCodesList.size()) return false;
        for(Inventory inventory : inventoryList) {
            String skuCode = inventory.getSkuCode();
            int availableQuantity = inventory.getQuantity();
            int requiredQuantity = skuCodeToQuantityMap.get(skuCode);
            if (availableQuantity < requiredQuantity) {
                log.info("Product with skuCode {} is not available in required quantity", skuCode);
                return false;
            }
        }
        return true;
    }
}
