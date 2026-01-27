package com.example.demo.mapper;

import com.example.demo.pojo.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CouponMapper {
    void insert(Coupon coupon);
    void update(Coupon coupon);
    Coupon findById(Long couponId);
    List<Coupon> findAvailable();
    int incrementReceiveCount(Long couponId);
}
