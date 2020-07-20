package com.leyou.page.mq;

import com.leyou.page.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: zourong
 * @Date: 2020/7/8 09:48
 * @Description: 接受mq消息
 */
@Component
public class PageListener {
    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "item.page.update.queue",durable = "true"),
                                            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
                                            key = {"item.insert","item.update"}))
    public void insertOrUpdatePage(Long spuId){
        pageService.insertOrUpdatePage(spuId);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "item.page.delete.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void deletePage(Long spuId){
        pageService.deletePage(spuId);
    }
}
