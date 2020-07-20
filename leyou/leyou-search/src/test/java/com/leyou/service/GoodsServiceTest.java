package com.leyou.service;

import com.leyou.client.GoodsClient;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTest {

    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsRepository goodsRepository;

    /**
     * @Author zourong
     * @Description 将商品信息存入ElasticSearch索引库
     * @Date 2020/6/28 14:51
     * @Param []
     * @return void
     **/
    @Test
    public void testBuildGoods(){
        int page=1;
        int rows=100;
        int size=0;
        do{
            PageResult<Spu> spuPageResult = goodsClient.querySpuByPage(null, true, page, rows);
            List<Spu> items = spuPageResult.getItems();
            if(CollectionUtils.isEmpty(items)){
                break;
            }
            size=items.size();
            List<Goods> goods = items.stream().map(goodsService::buildGoods).collect(Collectors.toList());
            goodsRepository.saveAll(goods);
            page++;
        }while (size == 100);

    }
}