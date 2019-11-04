package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.exceptions.ClientException;
import com.itcast.service.ValidateCodeService;
import com.itcast.utils.SMSUtils;
import com.itcast.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import static com.itcast.constant.RedisMessageConstant.SENDTYPE_LOGIN;
import static com.itcast.utils.SMSUtils.VALIDATE_CODE;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 20:53
 * @description ：
 */
@Service(interfaceClass = ValidateCodeService.class)
@Transactional
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送验证码
     *
     * @param phoneNumber
     * @param length
     * @param sendType
     * @param time
     * @throws ClientException
     */
    @Override
    public void sendValidateCode(String phoneNumber, Integer length, String sendType, Integer time) throws ClientException {
        Integer validateCode = ValidateCodeUtils.generateValidateCode(length);
        SMSUtils.sendShortMessage(VALIDATE_CODE, phoneNumber, validateCode.toString());
        jedisPool.getResource().setex(phoneNumber + sendType, time, validateCode.toString());
    }

    /**
     * 校验验证码
     *
     * @param telephone
     * @param validateCode
     * @return
     */
    @Override
    public Boolean checkValidateCode(String telephone, String validateCode) {

        String codeInRedis = jedisPool.getResource().get(telephone + SENDTYPE_LOGIN);
        if (validateCode != null && codeInRedis != null && validateCode.equals(codeInRedis)) {
            return true;
        }
        return false;
    }
}
