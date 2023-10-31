package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.service.SchoolService;
import com.yzj.risingpath_zsb_backend.mapper.SchoolMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【school】的数据库操作Service实现
* @createDate 2023-06-08 13:55:49
*/
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School>
    implements SchoolService{

    @Resource
    private SchoolMapper schoolMapper;

    /**
     * 公办数量
     * @return
     */
    @Override
    public int getPublicSchoolCount() {
        return schoolMapper.getPublicSchoolCount();
    }

    /**
     * 民办数量
     * @return
     */
    @Override
    public int getPrivateSchoolCount() {
        return schoolMapper.getPrivateSchoolCount();
    }

    @Override
    public List<School> getSchoolForSim(@Param("remarks") String remarks, @Param("type") String type) {
        return schoolMapper.getSchoolForSim(remarks,type);
    }
}




