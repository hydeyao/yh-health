package com.itcast.service;

import com.aliyuncs.exceptions.ClientException;

/**
 * @author ：yh
 * @version :
 * @date ：Created in 2019/10/16 20:53
 * @description ：
 */
public interface ValidateCodeService {

    /**
     * 发送验证码服务
     *
     * @param phoneNumber
     * @param length
     * @param sendType
     * @param time
     * @throws ClientException
     */
    void sendValidateCode(String phoneNumber, Integer length, String sendType, Integer time) throws ClientException;

    /**
     * 校验验证码
     * @param telephone
     * @param validateCode
     * @return
     */
    Boolean checkValidateCode(String telephone,String validateCode);

}
