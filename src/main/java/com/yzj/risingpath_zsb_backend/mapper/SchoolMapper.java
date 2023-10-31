package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.School;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【school】的数据库操作Mapper
* @createDate 2023-06-08 13:55:49
* @Entity com.yzj.risingpath_zsb_backend.domain.School
*/
public interface SchoolMapper extends BaseMapper<School> {

    @Select("SELECT COUNT(*) FROM school WHERE schoolPhone = '公办'")
    int getPublicSchoolCount();

    @Select("SELECT COUNT(*) FROM school WHERE schoolPhone = '民办'")
    int getPrivateSchoolCount();

    @Select("SELECT DISTINCT school.schoolCode, school.schoolName FROM professinfo, school  WHERE professinfo.schoolId = school.schoolId  \n" +
            "AND (professinfo.type = #{type}) AND (professinfo.remarks LIKE CONCAT('%', #{remarks}, '%') OR LENGTH(professinfo.remarks) <= 51 )")
    List<School> getSchoolForSim(@Param("remarks") String remarks,@Param("type") String type);
}




