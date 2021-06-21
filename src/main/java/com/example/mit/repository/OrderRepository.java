package com.example.mit.repository;

import com.example.mit.model.Order;
import com.example.mit.model.OrderState;
import com.example.mit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByUserAndOrderStateEquals(User user, OrderState state);


}
