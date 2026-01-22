package com.example.demo.service;

import com.example.demo.pojo.entity.Coupon;
import com.example.demo.pojo.entity.UserCoupon;
import java.util.List;

/**
 * 优惠券业务接口
 */
public interface CouponService {
    
    /**
     * 创建优惠券
     * @param coupon 优惠券信息
     */
    void createCoupon(Coupon coupon);
    
    /**
     * 查询可领取的优惠券
     * @return 优惠券列表
     */
    List<Coupon> listAvailable();
    
    /**
     * 领取优惠券
     * @param userId 用户ID
     * @param couponId 优惠券ID
     */
    void receiveCoupon(Long userId, Long couponId);
    
    /**
     * 查询我的优惠券
     * @param userId 用户ID
     * @param status 状态 (可选)
     * @return 用户优惠券列表
     */
    List<UserCoupon> listMyCoupons(Long userId, Integer status);
}
