package com.yzj.risingpath_zsb_backend.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public class MybatisUtil {

    //查找符合条件的记录是否存在
    public static <T> boolean existCheck(IService<T> service, Map<String,Object> map){
        QueryWrapper<T> wrapper=new QueryWrapper<>();
        map.forEach(wrapper::eq);
        return service.count(wrapper)>0;
    }
}