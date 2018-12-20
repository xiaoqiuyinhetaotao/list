package com.itheima.service;

import com.itheima.domain.Product;

import java.util.List;

public interface ProductService {
    public List<Product> findAll(Integer page, Integer size) throws Exception;

    void save(Product product) throws Exception;
}
