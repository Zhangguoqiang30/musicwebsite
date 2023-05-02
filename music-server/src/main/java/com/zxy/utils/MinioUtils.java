package com.zxy.utils;


import javax.imageio.ImageIO;

import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.policy.PolicyType;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MinIO工具类
 */

@Component
public class MinioUtils {

    private final Logger log = LoggerFactory.getLogger(MinioUtils.class);

    @Autowired
    private MinioClient minioClient;


    /**
     * 上传文件
     * 返回文件路径
     */
    public String uploadFile(String bucketName, String fileName, MultipartFile file, String contentType) throws InvalidBucketNameException, InsufficientDataException, XmlPullParserException, ErrorResponseException, NoSuchAlgorithmException, IOException, NoResponseException, InvalidKeyException, InternalException {
        try {
            if(!StringUtils.isNull(minioClient)){
                if (!minioClient.bucketExists(bucketName)) {
                    createBucket(bucketName);
                }
            }
        } catch (Exception e) {
            log.error("存储桶创建失败", e);
            throw new RuntimeException("存储桶创建失败");
        }

        InputStream inputStream = null;
        InputStream result = null;
        try {
            if (!minioClient.bucketExists(bucketName)) {
                minioClient.makeBucket(bucketName);
                minioClient.setBucketPolicy(bucketName, "", PolicyType.READ_ONLY);
            }
            inputStream = file.getInputStream();
            //如果是图片文件就进行压缩
            if (ImageUtil.isImage(fileName)) {
                result = ImageUtil.getInputStream(
                        ImageUtil.compress(
                                ImageIO.read(inputStream)),
                        ImageUtil.getFileExtention(fileName));
                minioClient.putObject(bucketName, fileName, result, contentType);
            } else {
                minioClient.putObject(bucketName, fileName, inputStream, contentType);
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败");
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(result);
        }
        return  minioClient.getObjectUrl(bucketName, fileName);
    }


    public String upload(MultipartFile file, String bucketName, String mediaType) throws InvalidBucketNameException, InsufficientDataException, XmlPullParserException, ErrorResponseException, NoSuchAlgorithmException, IOException, NoResponseException, InvalidKeyException, InternalException {
      return uploadFile(bucketName,file.getName(),file,file.getContentType());
    }

    /**
     * 文件上传至Minio服务器
     * @param file 上传文件
     * @param bucketName 存储桶名称
     */
    public String upload(MultipartFile file, String bucketName)  {
        String fileName = file.getOriginalFilename();
        String msg = "";
        try {
            msg = uploadFile(bucketName, fileName, file, file.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件上传失败！"+e.getMessage());
        }
        return msg;
        }




    /**
     * 上传文件
     */
    public String uploadFile(String bucketName, String fileName, File file, String contentType) {
        try {
            if (!minioClient.bucketExists(bucketName)) {
                createBucket(bucketName);
            }
        } catch (Exception e) {
            log.error("存储桶创建失败", e);
            throw new RuntimeException("存储桶创建失败");
        }

        InputStream inputStream = null;
        InputStream result = null;
        try {
            if (!minioClient.bucketExists(bucketName)) {
                minioClient.makeBucket(bucketName);
                minioClient.setBucketPolicy(bucketName, "", PolicyType.READ_ONLY);
            }
            inputStream = new FileInputStream(file);
            //如果是图片文件就进行压缩
            if (ImageUtil.isImage(fileName)) {
                result = ImageUtil.getInputStream(
                        ImageUtil.compress(
                                ImageIO.read(inputStream)),
                        ImageUtil.getFileExtention(fileName));
                minioClient.putObject(bucketName, fileName, result, contentType);
            } else {
                minioClient.putObject(bucketName, fileName, inputStream, contentType);
            }
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败");
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(result);
        }
        return fileName;
    }

    /**
     * 下载文件
     */
    public File downloadFile(String bucketName, String fileName) {
        try {
            InputStream stream = minioClient.getObject(bucketName, fileName);
            String[] name = fileName.split("\\.");
            return streamToFile(stream, name[0], "." + name[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     */
    public boolean deleteFile(String bucketName, String fileName) {
        try {
            minioClient.removeObject(bucketName, fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 输入流转虚拟文件
     */
    private File streamToFile(InputStream in, String prefix, String suffix) throws IOException {
        final File tempFile = File.createTempFile(prefix, suffix);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    public void createBucket(String bucketName) throws Exception {
        if (!minioClient.bucketExists(bucketName)) {
            minioClient.makeBucket(bucketName);
        }
    }
}
