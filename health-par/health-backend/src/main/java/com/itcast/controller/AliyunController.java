package com.itcast.controller;

import com.itcast.entity.Result;
import com.itcast.utils.AliyunUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

import static com.itcast.constant.MessageConstant.*;
import static com.itcast.constant.RedisConstant.*;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/13 8:45
 * @description ：aliYunOos Controller
 */
@RestController
@RequestMapping("alioos")
public class AliyunController {


    @Autowired
    private JedisPool jedisPool;

    /**
     * 文件上传
     *
     * @param imgFile
     * @return
     */
    @RequestMapping("/{part}/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile, @PathVariable("part") String part) {
        try {
            //原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            //获取文件后缀
            int lastIndex = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndex);

            //生成文件名
            String saveName = UUID.randomUUID().toString() + suffix;

            //保存到AliYun路径
            AliyunUtils.upload2Aliyun(imgFile.getBytes(), part + "/" + saveName);

            jedisPool.getResource().sadd(SETMEAL_PIC_RESOURCES, part + "/" + saveName);

            return new Result(true, PIC_UPLOAD_SUCCESS, saveName);
        } catch (Exception e) {
            return new Result(false, PIC_UPLOAD_FAIL);
        }
    }

}
