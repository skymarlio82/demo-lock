
package com.wiz.demo.lock.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wiz.demo.lock.data.entity.Order;

@Mapper
public interface OrderDao {

	List<Order> findByProductName(String productName);
	void add(Order order);
}