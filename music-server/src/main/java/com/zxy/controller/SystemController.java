package com.zxy.controller;

import com.zxy.utils.FileUtil;
import com.zxy.utils.MinioUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/system")
public class SystemController {

    @Resource
    private MinioUtils minioUtils;

    @GetMapping("/download")
    public void download(String bucket, String object, HttpServletResponse response) throws IOException {
        File file = minioUtils.downloadFile(bucket, object);
        ServletOutputStream outputStream = response.getOutputStream();
        String suffix = "";
        if(file.getName().lastIndexOf(".")>=0) {
            suffix = file.getName().substring(file.getName().lastIndexOf("."));
        }

        if(object!=null) {
            if (object.endsWith(".jpg") || object.endsWith(".png") || object.endsWith(".gif")||object.endsWith(".jpeg")) {
                response.setContentType("image/jpeg;charset=GBK");
            } else {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("档案文件", "UTF-8")+suffix);
            }
        }
        outputStream.write(FileUtil.FileToByte(file));
    }

//      gpt优化代码
//    @GetMapping("/download")
//    public void download(String bucket, String object, HttpServletResponse response) throws IOException {
//        File file = minioUtils.downloadFile(bucket, object);
//        try (ServletOutputStream outputStream = response.getOutputStream()) {
//            String extension = FilenameUtils.getExtension(file.getName());
//            if (extension.equals("jpg") || extension.equals("png") || extension.equals("gif") || extension.equals("jpeg")) {
//                response.setContentType("image/" + extension);
//            } else {
//                response.setContentType("application/octet-stream");
//                response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
//            }
//            outputStream.write(FileUtil.FileToByte(file));
//        }
//    }

}
