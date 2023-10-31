package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.School;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【school】的数据库操作Service
* @createDate 2023-06-08 13:55:49
*/
public interface SchoolService extends IService<School> {
    int getPublicSchoolCount();

    int getPrivateSchoolCount();

    List<School> getSchoolForSim(@Param("remarks") String remarks,@Param("type") String type);


}
