package com.example.priceservice.Dao;

import com.example.priceservice.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    Product findByUserIdAndProductCode(String userId, String productCode);
}
