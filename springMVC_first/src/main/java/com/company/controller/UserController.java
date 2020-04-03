package com.company.controller;

import com.company.domain.User;
import com.company.exception.MyException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/testUser")
    @ResponseBody
    public User testUser(User user){
        System.out.println(user);
        user.setName("returnName");
        user.setPassword("returnPwd");
        user.setAge(28);
        return user;
    }
    @RequestMapping("/upload1")
    public String upload1(HttpServletRequest request, MultipartFile file){
        String realPath = request.getSession().getServletContext().getRealPath("/");
        File dir=new File(realPath+File.separator+"upload");
        if(!dir.exists()){
            dir.mkdir();
        }
        try(FileOutputStream fos=new FileOutputStream(new File(dir.getAbsolutePath()+File.separator+ UUID.randomUUID()+file.getOriginalFilename()))) {
                fos.write(file.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping("/upload2")
    public String upload2(MultipartFile file){
        String path = "http://localhost:8090/upload/";
        //创建客户端对象
        Client client = Client.create();
        //和图片服务器进行连接
        WebResource resource = client.resource(path + UUID.randomUUID() + file.getOriginalFilename());
        //上传文件
        try {
            resource.put(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping("/testException")
    public String testException() throws MyException {
        try {
            int i=10/0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("测试异常。。");
        }
        return "success";
    }
}
