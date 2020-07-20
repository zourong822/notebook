package com.leyou.page.service.impl;

import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import com.leyou.item.pojo.*;
import com.leyou.page.service.PageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zourong
 * @Date: 2020/7/5 17:41
 * @Description:
 */
@Service
@Slf4j
public class PageServiceImpl implements PageService {

    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private SpecificationClient specClient;

    //网页静态化
    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public Map<String,Object> queryGoodsDetail(Long id) {
        Map<String,Object> model=new HashMap<>();
        //1、spu
        Spu spu = goodsClient.queryGoodsBySpuId(id);
        model.put("title",spu.getTitle());
        model.put("subTitle",spu.getSubTitle());
        //2、brand
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        model.put("brand",brand);
        //3、categories
        List<Category> categories = categoryClient.queryListByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        model.put("categories",categories);
        //4、detail
        SpuDetail detail = spu.getSpuDetail();
        model.put("detail",detail);
        //5、specs
        List<SpecGroup> specs = specClient.getSpecGroupsByCid(spu.getCid3(), true);
        model.put("specs",specs);
        //6、skus
        List<Sku> skus = spu.getSkus();
        model.put("skus",skus);
        return model;
    }

    @Override
    public void insertOrUpdatePage(Long spuId) {
        staticHtml(spuId);
    }

    @Override
    public void deletePage(Long spuId) {
        File file = new File("G:" + File.separator + "TEMP/" + spuId + ".html");
        if(file.exists()){
            file.delete();
        }
    }

    public void staticHtml(Long spuId){
        Map<String, Object> map = queryGoodsDetail(spuId);
        Context context=new Context();
        context.setVariables(map);
        File file = new File("G:" + File.separator + "TEMP/" + spuId + ".html");
        if(file.exists()){
            file.delete();
        }
        try (PrintWriter writer = new PrintWriter(file,"UTF-8")) {
            templateEngine.process("item", context, writer);
        }catch (Exception e){
            log.error("[页面静态化失败]",e);
        }
    }
}
