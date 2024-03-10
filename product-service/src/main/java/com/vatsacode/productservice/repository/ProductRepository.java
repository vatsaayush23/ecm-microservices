package com.vatsacode.productservice.repository;

import com.vatsacode.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    //
}
