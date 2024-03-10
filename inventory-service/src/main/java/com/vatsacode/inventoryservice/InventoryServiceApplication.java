package com.vatsacode.inventoryservice;

import com.vatsacode.inventoryservice.model.Inventory;
import com.vatsacode.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory1 = Inventory.builder()
					.skuCode("skuMotoG1")
					.quantity(100)
					.unitPrice(BigDecimal.valueOf(12999.99))
					.lastUpdated(LocalDateTime.now())
					.build();
			inventoryRepository.save(inventory1);
			Inventory inventory2 = Inventory.builder()
					.skuCode("skuMotoG2")
					.quantity(0)
					.unitPrice(BigDecimal.valueOf(14999.99))
					.lastUpdated(LocalDateTime.now())
					.build();
			inventoryRepository.save(inventory2);
		};
    }
}
