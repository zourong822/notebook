package com.leyou.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    /**
     * @Author zourong
     * @Description 上传图片
     * @Date 2020/6/12 18:18
     * @Param [file]
     * @return java.lang.String
     **/
    String uploadImage(MultipartFile file);
}
