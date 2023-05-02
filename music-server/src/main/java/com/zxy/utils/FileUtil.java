package com.zxy.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Component
public class FileUtil {

    public File MultiPartFile2File(MultipartFile multipartFile){
        if(multipartFile==null){
            try {
                throw new Exception("文件为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //文件上传前的名称
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(fileName);
        OutputStream out = null;
        try{
            //获取文件流，以文件流的方式输出到新文件
//    InputStream in = multipartFile.getInputStream();
            out = new FileOutputStream(file);
            byte[] ss = multipartFile.getBytes();
            for(int i = 0; i < ss.length; i++){
                out.write(ss[i]);
            }
            return file;
        }catch(IOException e){
            e.printStackTrace();
        }  finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File f = new File(file.toURI());
        if (f.delete()){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
        return null;
    }

    public static byte[] FileToByte(File tradeFile){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 判断一个文件路径情况
     * @param path
     * @return
     */
    public static int fileExists(String path)
    {
        File file = new File(path);
        if(file.isFile())
            return 1; // 是文件
        else if(file.isDirectory())
        {
            if(file.list().length>0)
                return 2;   // 非空文件夹
            else
                return 3;   // 空文件夹
        }
        return 0; // 路径不存在
    }


}
