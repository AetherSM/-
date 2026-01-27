package com.example.demo.service.impl;

import com.example.demo.constant.Messages;
import com.example.demo.expection.BusinessException;
import com.example.demo.mapper.CouponMapper;
import com.example.demo.mapper.UserCouponMapper;
import com.example.demo.pojo.entity.Coupon;
import com.example.demo.pojo.entity.UserCoupon;
import com.example.demo.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        if (coupon.getReceivedCount() == null) {
            coupon.setReceivedCount(0);
        }
        couponMapper.insert(coupon);
    }

    @Override
    public List<Coupon> listAvailable() {
        return couponMapper.findAvailable();
    }

    @Override
    @Transactional
    public void receiveCoupon(Long userId, Long couponId) {
        Coupon c = couponMapper.findById(couponId);
        if (c == null || c.getStatus() == null || c.getStatus() != 1) {
            throw new BusinessException(Messages.COUPON_INVALID);
        }
        if (c.getStartTime() != null && c.getStartTime().isAfter(java.time.LocalDateTime.now())) {
            throw new BusinessException(Messages.COUPON_NOT_STARTED);
        }
        if (c.getEndTime() != null && c.getEndTime().isBefore(java.time.LocalDateTime.now())) {
            throw new BusinessException(Messages.COUPON_EXPIRED);
        }
        int already = userCouponMapper.countByUserAndCoupon(userId, couponId);
        if (already > 0) {
            throw new BusinessException(Messages.COUPON_ALREADY_RECEIVED);
        }
        int updated = couponMapper.incrementReceiveCount(couponId);
        if (updated == 0) {
            throw new BusinessException(Messages.COUPON_OUT_OF_STOCK);
        }
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
