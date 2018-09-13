package com.wmt.carmanage.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件工具类
 */
@Slf4j
@ConfigurationProperties(prefix = "upload")
@Component
public class FileUtils {

    private static String upload_file_save_path;

    public void setUpload_file_save_path(String upload_path) {
        FileUtils.upload_file_save_path = upload_path;
    }

    @PostConstruct
    public void init(){
        // 初始化文件上传目录
        File file;
        if (upload_file_save_path == null) {
            file = new File(System.getProperty("user.home"), "upload_file");
        } else {
            file = new File(upload_file_save_path);
        }
        if (!file.exists()) {
            // 上传的文件夹不存在则新建
            if (!file.mkdirs()) {
                log.error("文件上传目录创建失败，请检查");
                throw new RuntimeException("文件上传目录创建失败，请检查");
            }
        }
        upload_file_save_path = file.getAbsolutePath();
    }

    public static String saveUploadFile(MultipartFile logoImage, String fileName) throws IOException {
        String contentType = logoImage.getContentType();
        String suffix = contentType.split("/")[1];
        fileName += "." + suffix;
        File file = new File(upload_file_save_path, fileName);
        logoImage.transferTo(file);
        return file.getAbsolutePath();
    }

    /**
     * 读取文件
     * @param imgName
     * @return
     * @throws IOException
     */
    public static  String IoReadImage(String imgName) throws IOException {
        OutputStream ops = null;
        InputStream ips = null;
        try {
            //获取图片存放路径
            File file  = new File(imgName);
            Path p1    = file.toPath();
            ops = Files.newOutputStream(p1);
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                ops.write(buffer,0,len);
            }
            ops.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ops.close();
            ips.close();
        }
        return null;
    }
}
