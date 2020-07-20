package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SpecificationApi {
    /**
     * @Author zourong
     * @Description 查询参数集合
     * @Date 2020/6/28 10:16
     * @Param [gid 组id, cid 分类id, searching 是否搜索]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecParam>>
     **/
    @GetMapping("/spec/params")
    List<SpecParam> querySpecParamList(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching
    );

    /**
     * @Author zourong
     * @Description 根据cid查询规格参数组
     * @Date 2020/7/6 7:51
     * @Param [cid, includeParams:是否包含组下参数列表]
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
     **/
    @GetMapping("/spec/groups/{cid}")
    List<SpecGroup> getSpecGroupsByCid(@PathVariable("cid") Long cid,
                                                              @RequestParam(value = "includeParams", required = false,defaultValue = "false")Boolean includeParams);
}
