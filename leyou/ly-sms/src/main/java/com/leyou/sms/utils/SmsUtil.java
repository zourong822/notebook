package com.leyou.sms.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.config.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: zourong
 * @Date: 2020/7/13 10:23
 * @Description:
 */
@Slf4j
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtil {

    @Autowired
    private SmsProperties prop;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static String SMS_KEY="sms:phone:";

    public void sendSms(String phoneNumbers,String signName,String templateCode,String templateParam){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", prop.getAccessKeyId(), prop.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumbers);//手机号码
        request.putQueryParameter("SignName", signName);//短信签名名称
        request.putQueryParameter("TemplateCode", templateCode);//短信模板ID
        request.putQueryParameter("TemplateParam", templateParam);
        try {
            CommonResponse response = client.getCommonResponse(request);
            BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(SMS_KEY+phoneNumbers);
            //限流
            if(StringUtils.isBlank(ops.get())){
                ops.set("0",60,TimeUnit.SECONDS);
            }else {
                return;
            }
            //log
            Map<String,String> kvMap = JsonUtils.parseMap(response.getData(), String.class, String.class);
            log.info("[短信发送返回数据]{}",kvMap);
        } catch (ServerException e) {
            log.error("[发送短信失败]",e);
        } catch (ClientException e) {
            log.error("[发送短信失败]",e);
        }

    }
}
