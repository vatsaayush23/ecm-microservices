package com.vatsacode.inventoryservice.controller;

import com.vatsacode.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam Map<String, String> params) {
        Map<String, Integer> skuCodeToQuantityMap = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            skuCodeToQuantityMap.put(entry.getKey(), Integer.parseInt(entry.getValue()));
        }
        return inventoryService.isInStock(skuCodeToQuantityMap);
    }
}
