package com.leyou.page.controller;

import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Auther: zourong
 * @Date: 2020/7/4 16:55
 * @Description:
 */
@Controller
public class PageController {
    @Autowired
    private PageService pageService;

    /**
     * @Author zourong
     * @Description 根据商品id查询商品详情
     * @Date 2020/7/5 17:40
     * @Param [id]
     * @return java.lang.String
     **/
    @GetMapping("/item/{id}.html")
    public String getItem(@PathVariable("id")Long id,Model model){
        Map<String, Object> dataMap = pageService.queryGoodsDetail(id);
        model.addAllAttributes(dataMap);
        return "item";
    }
}
