package com.zxy.bean.vo;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    private String endpoint;

    private int port;

    private String accessKey;

    private String secretKey;

    private String fileBucketName;

    public void setEndpoint(String endpoint){
        this.endpoint=endpoint;
    }

    public String getEndpoint(){
        return this.endpoint;
    }

    public void setPort(int port){
        this.port=port;
    }

    public int getPort(){
        return this.port;
    }

    public void setAccessKey(String accessKey){
        this.accessKey=accessKey;
    }

    public String getAccessKey(){
        return this.accessKey;
    }

    public void setSecretKey(String secretKey){
        this.secretKey=secretKey;
    }

    public String getSecretKey(){
        return this.secretKey;
    }

    public String getFileBucketName() {
        return this.fileBucketName;
    }

    public void setFileBucketName(String fileBucketName) {
        this.fileBucketName = fileBucketName;
    }

    @Bean
    public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
        MinioClient minioClient = new MinioClient(endpoint, port, accessKey, secretKey);
        return minioClient;
    }
}
