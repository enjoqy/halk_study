package com.leyou.upload.service;

import ch.qos.logback.classic.pattern.LoggerConverter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Author halk
 * @Date 2020/3/25 22:43
 */
@Service
public class UploadService {

    /**
     * 只接受请求头中有效的文件类型
     */
    private static final List<String> CONTENT_TYPE = Arrays.asList("application/x-img", "image/jpeg", "application/x-jpg", "image/png", "application/x-png");

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    public String uploadImage(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();

        //校验文件类型

        //1.都可以截取后缀名进行一一判断
        String suffixName = StringUtils.substringAfterLast(originalFilename, ",");

        //2.根据请求头中的文件类型进行判断
        String contentType = file.getContentType();
        if (!CONTENT_TYPE.contains(contentType)){
            LOGGER.info("文件类型不合法：{}", originalFilename);
            return null;
        }

        try {
            //检验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null){
                LOGGER.info("文件内容不合法：{}", originalFilename);
                return null;
            }

            //保存到文件服务器
            file.transferTo(new File("D:\\projects\\halk_study\\image\\" + originalFilename));

            //返回图片url路径
            return "http://image.leyou.com/" + originalFilename;
        } catch (IOException e) {
            LOGGER.info("文件服务器内部错误：{}" + originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}