package com.leyou.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.mapper.SpecGroupMapper;
import com.leyou.mapper.SpecParamMapper;
import com.leyou.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zourong
 * @Date: 2020/6/17 09:15
 * @Description:
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> getGroupByCid(Long cid, Boolean includeParams) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> list = specGroupMapper.select(specGroup);

        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_IS_EMPTY);
        }
        //仅当includeParams为true才查询组下参数
        if(includeParams){
            //查询出组对应的params
            List<SpecParam> paramList = getParamList(null, cid, null);
            Map<Long,List<SpecParam>> paramMap=new HashMap<>();
            for (SpecParam param : paramList) {
                if(null ==paramMap.get(param.getGroupId())){
                    paramMap.put(param.getGroupId(),new ArrayList<>());
                }
                paramMap.get(param.getGroupId()).add(param);
            }

            for (SpecGroup group : list) {
                group.setParams(paramMap.get(group.getId()));
            }
        }
        return list;
    }

    @Override
    public List<SpecParam> getParamList(Long gid,Long cid,Boolean searching) {
        SpecParam param=new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(param);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.SPEC_PARAM_IS_EMPTY);
        }
        return list;
    }
}
