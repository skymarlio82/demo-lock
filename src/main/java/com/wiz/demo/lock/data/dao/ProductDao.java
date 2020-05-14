
package com.wiz.demo.lock.data.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wiz.demo.lock.data.entity.Product;

@Mapper
public interface ProductDao {

	Product findById(int id);
	void update(Product product);
}