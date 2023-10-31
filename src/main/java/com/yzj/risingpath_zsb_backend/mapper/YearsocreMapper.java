package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzj.risingpath_zsb_backend.domain.vo.ScoreAndProfessinfoVo;
import com.yzj.risingpath_zsb_backend.domain.vo.SmartVolunteerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 姚浙杰
 * @description 针对表【yearsocre】的数据库操作Mapper
 * @createDate 2023-06-02 16:02:48
 * @Entity com.yzj.risingpath_zsb_backend.domain.Yearsocre
 */
public interface YearsocreMapper extends BaseMapper<Yearsocre> {



    @Select("<script>" +
            "SELECT professinfo.type,professinfo.proCode,\n" +
            "       school.schoolName,\n" +
            "       professinfo.professName,\n" +
            "       school.city,\n" +
            "       MAX(CASE WHEN year = 2021 THEN minScore ELSE NULL END) AS 'twoYearBefore', MAX(CASE WHEN year = 2022 THEN minScore ELSE NULL END) AS 'oneYearBefore', MAX(CASE WHEN year = 2023 THEN minScore ELSE NULL END) AS 'currentYear', professinfo.remarks\n" +
            "FROM school,\n" +
            "     professinfo,\n" +
            "     yearsocre\n" +
            "where yearsocre.proId = professinfo.proId\n" +
            "  AND yearsocre.schoolId = school.schoolId\n" +
            "<if test='schoolName != null and schoolName.length>0'>AND school.schoolName LIKE CONCAT('%', #{schoolName}, '%')</if>" +
            "GROUP BY yearsocre.schoolId, yearsocre.proId" +
            "</script>")
    List<ScoreAndProfessinfoVo> getScoreBySchoolName(@Param("schoolName") String schoolName);

    /**
     * 专业名模糊查询信息
     */
    @Select("<script>" +
            "SELECT professinfo.type,professinfo.proCode,\n" +
            "       school.schoolName,\n" +
            "       professinfo.professName,\n" +
            "       school.city,\n" +
            "       MAX(CASE WHEN year = 2021 THEN minScore ELSE NULL END) AS 'twoYearBefore', MAX(CASE WHEN year = 2022 THEN minScore ELSE NULL END) AS 'oneYearBefore', MAX(CASE WHEN year = 2023 THEN minScore ELSE NULL END) AS 'currentYear', professinfo.remarks\n" +
            "FROM school,\n" +
            "     professinfo,\n" +
            "     yearsocre\n" +
            "where yearsocre.proId = professinfo.proId\n" +
            "  AND yearsocre.schoolId = school.schoolId\n" +
            "<if test='professName != null and professName.length>0'>AND professinfo.professName LIKE CONCAT('%', #{professName}, '%')</if>" +
            "GROUP BY yearsocre.schoolId, yearsocre.proId" +
            "</script>")
    List<ScoreAndProfessinfoVo> getScoreByProfessinfo(@Param("professName") String professName);

    /**
     * 获取分数
     * @param remarks
     * @return
     */

    @Select("SELECT DISTINCT professinfo.type,professinfo.proCode,school.schoolName, professinfo.professName, school.city, \n" +
            "       MAX(CASE WHEN year = 2021 THEN minScore ELSE NULL END) AS 'twoYearBefore', \n" +
            "       MAX(CASE WHEN year = 2022 THEN minScore ELSE NULL END) AS 'oneYearBefore', \n" +
            "       MAX(CASE WHEN year = 2023 THEN minScore ELSE NULL END) AS 'currentYear', \n" +
            "       professinfo.remarks \n" +
            "FROM school, professinfo, yearsocre \n" +
            "WHERE yearsocre.proId = professinfo.proId \n" +
            "  AND yearsocre.schoolId = school.schoolId \n" +
            "  AND (professinfo.remarks LIKE CONCAT('%', #{remarks}, '%') OR LENGTH(professinfo.remarks) <= 51) \n" +
            "GROUP BY yearsocre.schoolId, yearsocre.proId;\n")
    List<ScoreAndProfessinfoVo> getScoreByRemarks(@Param("remarks") String remarks);


}




