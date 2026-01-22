package com.example.demo.service.impl;

import com.example.demo.mapper.CouponMapper;
import com.example.demo.mapper.UserCouponMapper;
import com.example.demo.pojo.entity.Coupon;
import com.example.demo.pojo.entity.UserCoupon;
import com.example.demo.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;
    
    @Autowired
    private UserCouponMapper userCouponMapper;

    @Override
    public void createCoupon(Coupon coupon) {
        coupon.setStatus(1);
        couponMapper.insert(coupon);
    }

    @Override
    public List<Coupon> listAvailable() {
        return couponMapper.findAll(); // 简化逻辑，实际应过滤时间和状态
    }

    @Override
    public void receiveCoupon(Long userId, Long couponId) {
        UserCoupon uc = new UserCoupon();
        uc.setUserId(userId);
        uc.setCouponId(couponId);
        userCouponMapper.insert(uc);
    }

    @Override
    public List<UserCoupon> listMyCoupons(Long userId, Integer status) {
        return userCouponMapper.listByUser(userId, status);
    }
}
