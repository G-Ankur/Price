package com.priceservice.Dao;

import com.priceservice.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    Product findByUserIdAndProductCode(String userId, String productCode);
}
