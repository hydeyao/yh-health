package com.itcast.jobs;

import com.itcast.utils.AliyunUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

import static com.itcast.constant.RedisConstant.*;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/13 14:17
 * @description ：定时清理垃圾图片任务类
 */
public class CleanImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void cleanImg() {

        Set<String> set = jedisPool.getResource().sdiff(SETMEAL_PIC_RESOURCES, SETMEAL_PIC_DB_RESOURCES);
        if (set != null && set.size() != 0) {
            for (String fileName : set) {
                AliyunUtils.deleteFileFromAli(fileName);
                jedisPool.getResource().srem(SETMEAL_PIC_RESOURCES,fileName);
            }

        }

    }

}
