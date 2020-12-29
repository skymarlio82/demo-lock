package com.wiz.demo.lock.data.dao;

import com.wiz.demo.lock.data.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDao {

    Product findById(int id);
    void update(Product product);
}