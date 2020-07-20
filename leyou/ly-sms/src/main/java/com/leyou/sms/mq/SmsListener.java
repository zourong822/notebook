package com.leyou.sms.mq;

import com.leyou.common.utils.JsonUtils;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Auther: zourong
 * @Date: 2020/7/13 10:47
 * @Description:
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private SmsProperties prop;

    /**
     * @Author zourong
     * @Description 发送短信验证码
     * @Date 2020/7/13 10:53
     * @Param [phoneNumber]
     * @return void
     **/
    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "sms.verify.code.queue",durable = "true"),
                                exchange = @Exchange(name = "ly.sms.exchange",type = ExchangeTypes.TOPIC),
                                key = "sms.verify.code"))
    public void sendVerifySms(Map<String,String> paramMap){
        if(CollectionUtils.isEmpty(paramMap)){
            return;
        }
        String phone=paramMap.remove("phone");
        String code = paramMap.get("code");
        if(StringUtils.isBlank(phone) || StringUtils.isBlank(code)){
            return;
        }
        smsUtil.sendSms(phone,prop.getSignName(),prop.getTemplateCode(), JsonUtils.serialize(paramMap));
    }
}
