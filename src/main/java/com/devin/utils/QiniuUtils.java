package com.devin.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;


@Slf4j
public class QiniuUtils {

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = "evzPrTT1qVnC1WIcOAeJ763Ar9teglDAJVavCrCQ";
    private static final String SECRET_KEY = "nUECECMhOHxp5ZZHiKCg50Yezn4WbZg_EtkhmBLC";
    //要上传的空间 bucket name
    private static final String BUCKET_NAME = "haoziweizhi1111";

    /**
     * @param localFilePath
     * @param fileName
     * @return
     * @throws QiniuException
     */
    public static String easyUpLoad(File localFilePath, String fileName) throws QiniuException {
        return intoResult(null, localFilePath, null, fileName, null);
    }

    /**
     * @param uploadBytes
     * @param fileName
     * @return
     * @throws QiniuException
     */
    public static String byteUpLoad(byte[] uploadBytes, String fileName) throws QiniuException {
        return intoResult(uploadBytes, null, null, fileName, null);
    }

    /**
     * @param localFilePath
     * @param fileName
     * @return
     * @throws QiniuException
     */
    public static String byteUpLoadByName(File localFilePath, String fileName) throws QiniuException {
        return intoResultByName(null, localFilePath, null, fileName, null);
    }

    public static InputStream download(String url) {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                assert body != null;
                return body.byteStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
        return null;
    }


    /**
     * @param byteInputStream
     * @param fileName
     * @return
     * @throws QiniuException
     */
    public static String byteArrayInputStreamUpLoad(ByteArrayInputStream byteInputStream, String fileName) throws QiniuException {
        return intoResult(null, null, byteInputStream, fileName, null);
    }

    /**
     * @param localFilePath
     * @param fileName
     * @return
     */
    public static String recorderUpLoad(File localFilePath, String fileName) {
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), BUCKET_NAME).toString();
        try {
            //设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            return intoResult(null, localFilePath, null, fileName, fileRecorder);
        } catch (IOException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        }
    }

    private static String intoResult(byte[] uploadBytes, File localFilePath, ByteArrayInputStream byteInputStream, String fileName, FileRecorder fileRecorder) throws QiniuException {
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
//        String substring = fileName.substring(fileName.lastIndexOf("."));

        String key = "advance/" + System.currentTimeMillis() + fileName;

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET_NAME);
        Response response = null;
        try {
            if (uploadBytes != null && localFilePath == null && byteInputStream == null) {
                response = uploadManager.put(uploadBytes, key, upToken);
            }
            if (localFilePath != null && uploadBytes == null && byteInputStream == null) {
                response = uploadManager.put(localFilePath, key, upToken);
            }
            if (byteInputStream != null && uploadBytes == null && localFilePath == null) {
                response = uploadManager.put(byteInputStream, key, upToken, null, null);
            }
            if (response == null) {
                return "参数错误";
            }

            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            throw new QiniuException(r);
        }
    }

    private static String intoResultByName(byte[] uploadBytes, File localFilePath, ByteArrayInputStream byteInputStream, String fileName, FileRecorder fileRecorder) throws QiniuException {
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
//        String substring = fileName.substring(fileName.lastIndexOf("."));

        String key = "advance/" + fileName;
        //上传凭证
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET_NAME);
        Response response = null;
        try {
            if (uploadBytes != null && localFilePath == null && byteInputStream == null) {
                response = uploadManager.put(uploadBytes, key, upToken);
            }
            if (localFilePath != null && uploadBytes == null && byteInputStream == null) {
                response = uploadManager.put(localFilePath, key, upToken);
            }
            if (byteInputStream != null && uploadBytes == null && localFilePath == null) {
                response = uploadManager.put(byteInputStream, key, upToken, null, null);
            }
            if (response == null) {
                return "参数错误";
            }

            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            throw new QiniuException(r);
        }
    }

}
