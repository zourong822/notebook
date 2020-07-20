package com.leyou.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.cart.config.UploadProperties;
import com.leyou.service.UploadService;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: zourong
 * @Date: 2020/6/12 18:17
 * @Description:
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {
    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadProperties prop;

    public static final List<String> ALLOW_FILE_TYPE=Arrays.asList("image/jpeg","image/jpg","image/bmp","image/png");
    @Override
    public String uploadImage(MultipartFile file) {

        try {
            //校验上传过来的是否为图片
            if(!prop.getAllowTypes().contains(file.getContentType())){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            BufferedImage image=ImageIO.read(file.getInputStream());
            if(null == image){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            //上传图片
            StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), StringUtils.substringAfterLast(file.getOriginalFilename(), "."), null);
//            File dir=new File("G:"+File.separator+"uploadImage");
//            if(!dir.exists()){
//                dir.mkdir();
//            }
//            file.transferTo(new File(dir,file.getOriginalFilename()));
//            return dir.toString()+file.getOriginalFilename();
            return prop.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            log.error("上传图片失败",e);
            return null;
        }


    }
}
