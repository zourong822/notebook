package com.leyou.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

public interface SpecificationService {
    List<SpecGroup> getGroupByCid(Long cid, Boolean includeParams);

    List<SpecParam> getParamList(Long gid,Long cid,Boolean searching);
}
