package com.leyou.mq;

import com.leyou.service.GoodsService;
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
public class SearchListener {
    @Autowired
    private GoodsService goodsService;

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "item.search.update.queue",durable = "true"),
                                            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
                                            key = {"item.insert","item.update"}))
    public void insertOrUpdateIndex(Long spuId){
        goodsService.insertOrUpdateIndex(spuId);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "item.search.delete.queue",durable = "true"),
            exchange = @Exchange(name = "leyou.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}))
    public void deleteIndex(Long spuId){
        goodsService.deleteIndex(spuId);
    }
}
