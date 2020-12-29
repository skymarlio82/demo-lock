package com.wiz.demo.lock.data.dao;

import com.wiz.demo.lock.data.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {

    List<Order> findByProductName(String productName);
    void add(Order order);
}