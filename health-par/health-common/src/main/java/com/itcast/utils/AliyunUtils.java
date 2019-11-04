package com.itcast.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author ：yh
 * @version :1.0.1
 * @date ：Created in 2019/10/10 20:16
 * @description ：阿里云工具类
 */
public class AliyunUtils {

    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAI4Fd9vYfJzsEeG9rCLxbo";
    private static String accessKeySecret = "nUjHVF1PUvhkxQEcFiHsUKnznuHyfx";
    private static String bucketName = "yh-itheima-health";

    /**
     * 文件上传
     * @param filePath
     * @param fileName
     */
    public static void upload2Aliyun(String filePath, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
        ossClient.putObject(bucketName, fileName, new File(filePath));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 文件上传
     * @param bytes
     * @param fileName
     */
    public static void upload2Aliyun(byte[] bytes, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
        // 上传Byte数组。
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除操作
     * @param fileName
     */
    public static void deleteFileFromAli(String fileName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。
        ossClient.deleteObject(bucketName, fileName);
        // 关闭OSSClient。
        ossClient.shutdown();


    }


}
