package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.constant.Messages;
import com.example.demo.pojo.dto.ProductCreateDTO;
import com.example.demo.pojo.entity.Product;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "商品管理", description = "商品信息查询与管理接口")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    /**
     * 搜索商品列表
     * @param categoryId 分类ID
     * @param keyword 关键词
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param status 状态
     * @param sellerId 商家ID
     * @param sortBy 排序字段
     * @param order 排序方式
     * @return 商品列表
     */
    @GetMapping
    @Operation(summary = "搜索商品", description = "根据条件搜索商品列表")
    public Result<List<Product>> list(@Parameter(description = "分类ID") @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                      @Parameter(description = "搜索关键词") @RequestParam(value = "keyword", required = false) String keyword,
                                      @Parameter(description = "最低价格") @RequestParam(value = "minPrice", required = false) String minPrice,
                                      @Parameter(description = "最高价格") @RequestParam(value = "maxPrice", required = false) String maxPrice,
                                      @Parameter(description = "商品状态") @RequestParam(value = "status", required = false) Integer status,
                                      @Parameter(description = "商家ID") @RequestParam(value = "sellerId", required = false) Long sellerId,
                                      @Parameter(description = "排序字段") @RequestParam(value = "sortBy", required = false) String sortBy,
                                      @Parameter(description = "排序方式: asc/desc") @RequestParam(value = "order", required = false) String order) {
        return Result.success(productService.search(categoryId, keyword, minPrice, maxPrice, status, sellerId, sortBy, order));
    }

    /**
     * 获取商品详情
     * @param id 商品ID
     * @return 商品详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "商品详情", description = "根据ID获取商品详细信息")
    public Result<Product> detail(@PathVariable Long id) {
        return Result.success(productService.findById(id));
    }

    /**
     * 创建商品（仅商家）
     * @param dto 商品创建信息
     * @return 创建的商品
     */
    @PostMapping
    @Operation(summary = "创建商品", description = "商家发布新商品")
    public Result<Product> create(@RequestBody ProductCreateDTO dto) {
        Long sellerId = requireSeller();
        return Result.success(productService.create(sellerId, dto));
    }

    /**
     * 更新商品信息（仅商家）
     * @param id 商品ID
     * @param dto 更新信息
     * @return 更新后的商品
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新商品", description = "商家更新商品信息")
    public Result<Product> update(@PathVariable Long id, @RequestBody ProductCreateDTO dto) {
        Long sellerId = requireSeller();
        return Result.success(productService.update(id, sellerId, dto));
    }

    /**
     * 更改商品状态（仅商家）
     * @param id 商品ID
     * @param dto 包含状态信息
     * @return 无
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "更改商品状态", description = "商家上架或下架商品")
    public Result<Void> changeStatus(@PathVariable Long id, @RequestBody ProductCreateDTO dto)  {
        Long sellerId = requireSeller();
        productService.updateStatus(id, dto.getStatus(), sellerId);
        return Result.success();
    }

    /**
     * 调整库存（仅商家）
     * @param id 商品ID
     * @param dto DTO
     * @param delta 增量（正数增加，负数减少）
     * @param stock 直接设置库存值
     * @return 无
     */
    @PatchMapping("/{id}/stock")
    @Operation(summary = "调整库存", description = "商家调整商品库存")
    public Result<Void> adjustStock(@PathVariable Long id, @RequestBody ProductCreateDTO dto,
                                    @Parameter(description = "库存增量（正增负减）") @RequestParam(value = "delta", required = false) Integer delta,
                                    @Parameter(description = "直接设置库存值") @RequestParam(value = "stock", required = false) Integer stock) {
        Long sellerId = requireSeller();
        productService.adjustStock(id, stock, delta, sellerId);
        return Result.success();
    }

    private Long requireSeller() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) throw new IllegalArgumentException(Messages.UNAUTHORIZED);
        UserEntity user = userService.findById(userId);
        if (user == null || user.getUserType() == null || user.getUserType() != 3) {
            throw new IllegalArgumentException(Messages.ONLY_SELLER_PRODUCT);
        }
        return userId;
    }
}
