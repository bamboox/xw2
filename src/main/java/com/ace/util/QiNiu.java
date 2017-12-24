package com.ace.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;

/**
 * Created by bamboo on 17-12-12.
 */
public class QiNiu {//设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "mAVv03XPaSij-Ev5rB-bLWTRI_-n2Avno8N4_DD5";
    String SECRET_KEY = "5m0KeczJkw34VCHZU-5kV9SyPiXZ-2kFjMh40yhG";
    //要上传的空间
    String bucketname = "bamboo";
    //上传到七牛后保存的文件名
    //上传文件的路径
    String FilePath = "/home/bamboo/1.jpg";

    //密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    Zone z = Zone.zone2();
    Configuration c = new Configuration(z);
    //创建上传对象
    UploadManager uploadManager = new UploadManager(c);

    //设置callbackUrl以及callbackBody,七牛将文件名和文件大小回调给业务服务器
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public void upload(String file) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(file, null, getUpToken());
            //打印返回的信息
        } catch (QiniuException e) {

        }
    }

    public void upload(File file) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(file, null, getUpToken());
            //打印返回的信息
        } catch (QiniuException e) {

        }
    }

    public void upload(byte[] data, String key) throws IOException {
        try {
            //调用put方法上传
            Response res = uploadManager.put(data, key, getUpToken());
            //打印返回的信息
        } catch (QiniuException e) {

        }
    }
}
