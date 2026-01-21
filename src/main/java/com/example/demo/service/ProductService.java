package com.example.demo.service;

import com.example.demo.pojo.dto.ProductCreateDTO;
import com.example.demo.pojo.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> search(Integer categoryId, String keyword, String minPrice, String maxPrice, Integer status, Long sellerId, String sortBy, String order);

    Product findById(Long productId);

    Product create(Long sellerId, ProductCreateDTO dto);

    Product update(Long productId, Long sellerId, ProductCreateDTO dto);

    void updateStatus(Long productId, Integer status, Long sellerId);

    void adjustStock(Long productId, Integer stock, Integer delta, Long sellerId);
}

