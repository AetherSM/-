package com.example.demo.mapper;

import com.example.demo.pojo.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    void insert(Product product);

    void update(Product product);

    void updateStatus(@Param("productId") Long productId, @Param("status") Integer status);

    void updateStock(@Param("productId") Long productId, @Param("stock") Integer stock, @Param("delta") Integer delta);

    Product findById(@Param("productId") Long productId);

    List<Product> search(@Param("categoryId") Integer categoryId,
                         @Param("keyword") String keyword,
                         @Param("minPrice") String minPrice,
                         @Param("maxPrice") String maxPrice,
                         @Param("status") Integer status,
                         @Param("sellerId") Long sellerId,
                         @Param("sortBy") String sortBy,
                         @Param("order") String order);
}

