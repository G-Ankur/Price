package com.example.priceservice.Dao;

import com.example.priceservice.Model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceDAO extends JpaRepository<Price, Integer> {
    Price findByPriceId(Integer Id);
}
