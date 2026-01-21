package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.dto.ProductCreateDTO;
import com.example.demo.pojo.entity.Product;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<List<Product>> list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                      @RequestParam(value = "keyword", required = false) String keyword,
                                      @RequestParam(value = "minPrice", required = false) String minPrice,
                                      @RequestParam(value = "maxPrice", required = false) String maxPrice,
                                      @RequestParam(value = "status", required = false) Integer status,
                                      @RequestParam(value = "sellerId", required = false) Long sellerId,
                                      @RequestParam(value = "sortBy", required = false) String sortBy,
                                      @RequestParam(value = "order", required = false) String order) {
        return Result.success(productService.search(categoryId, keyword, minPrice, maxPrice, status, sellerId, sortBy, order));
    }

    @GetMapping("/{id}")
    public Result<Product> detail(@PathVariable Long id) {
        return Result.success(productService.findById(id));
    }

    @PostMapping
    public Result<Product> create(@RequestBody ProductCreateDTO dto) {
        Long sellerId = requireSeller();
        return Result.success(productService.create(sellerId, dto));
    }

    @PutMapping("/{id}")
    public Result<Product> update(@PathVariable Long id, @RequestBody ProductCreateDTO dto) {
        Long sellerId = requireSeller();
        return Result.success(productService.update(id, sellerId, dto));
    }

    @PatchMapping("/{id}/status")
    public Result<Void> changeStatus(@PathVariable Long id, @RequestBody ProductCreateDTO dto) {
        Long sellerId = requireSeller();
        productService.updateStatus(id, dto.getStatus(), sellerId);
        return Result.success();
    }

    @PatchMapping("/{id}/stock")
    public Result<Void> adjustStock(@PathVariable Long id, @RequestBody ProductCreateDTO dto,
                                    @RequestParam(value = "delta", required = false) Integer delta,
                                    @RequestParam(value = "stock", required = false) Integer stock) {
        Long sellerId = requireSeller();
        productService.adjustStock(id, stock, delta, sellerId);
        return Result.success();
    }

    private Long requireSeller() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) throw new IllegalArgumentException("未登录");
        UserEntity user = userService.findById(userId);
        if (user == null || user.getUserType() == null || user.getUserType() != 3) {
            throw new IllegalArgumentException("仅商家可操作商品");
        }
        return userId;
    }
}

