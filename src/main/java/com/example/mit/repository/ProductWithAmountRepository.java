package com.example.mit.repository;

import com.example.mit.model.Order;
import com.example.mit.model.ProductWithAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductWithAmountRepository extends JpaRepository<ProductWithAmount,Long> {
    List<ProductWithAmount> findAllByOrder(Order order);
}
