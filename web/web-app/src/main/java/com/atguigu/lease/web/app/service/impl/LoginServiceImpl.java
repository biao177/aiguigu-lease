package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.common.exception.LeaseException;
import com.atguigu.lease.common.result.ResultCodeEnum;
import com.atguigu.lease.common.sms.AliyunSMSProperties;
import com.atguigu.lease.common.utils.VerifyCodeUtil;
import com.atguigu.lease.web.app.service.LoginService;
import com.atguigu.lease.web.app.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AliyunSMSProperties aliyunSMSProperties;

    @Override
    public void getSMSCode(String phone) {
        //1. 检查手机号码是否为空
        if (!StringUtils.hasText(phone)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }

        //2. 检查Redis中是否已经存在该手机号码的key
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            //若存在，则检查其存在的时间
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC) {
                //若存在时间不足一分钟，响应发送过于频繁
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }

        //3.发送短信，并将验证码存入Redis
        String verifyCode = VerifyCodeUtil.getVerifyCode(4);
        if (aliyunSMSProperties.getEnable()) {
            smsService.sendCode(phone, verifyCode);
            redisTemplate.opsForValue().set(key, verifyCode, RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, verifyCode, RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
            // 若没有开启，通知前端，模拟出验证码功能
            throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_MOCK.getMessage() + " : [ " + verifyCode + " ]", ResultCodeEnum.APP_SEND_SMS_MOCK.getCode());
        }
    }
}
