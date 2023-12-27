package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzj.risingpath_zsb_backend.domain.vo.ScoreAndProfessinfoVo;
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
            "SELECT COUNT(*)" +
            "FROM school,\n" +
            "     professinfo,\n" +
            "     yearsocre\n" +
            "where yearsocre.proId = professinfo.proId\n" +
            "  AND yearsocre.schoolId = school.schoolId\n" +
            "<if test='schoolName != null and schoolName.length>0'>AND school.schoolName LIKE CONCAT('%', #{schoolName}, '%')</if>" +
            "</script>")
    Integer selectScoreBySchoolNameCount(@Param("schoolName") String schoolName);

    @Select("<script>" +
            "SELECT COUNT(*)" +
            "FROM school,\n" +
            "     professinfo,\n" +
            "     yearsocre\n" +
            "where yearsocre.proId = professinfo.proId\n" +
            "  AND yearsocre.schoolId = school.schoolId\n" +
            "<if test='professName != null and professName.length>0'>AND professinfo.professName LIKE CONCAT('%', #{professName}, '%')</if>" +
            "</script>")
    Integer selectScoreByProfessNameCount(@Param("professName") String professName);

    @Select("SELECT COUNT(*)" +
            "FROM school, professinfo, yearsocre " +
            "WHERE yearsocre.proId = professinfo.proId " +
            "  AND yearsocre.schoolId = school.schoolId " +
            "  AND (professinfo.remarks LIKE CONCAT('%', #{remarks}, '%') OR LENGTH(professinfo.remarks) <= 51) \n")
    Integer selectSocreByRemarkCount(@Param("remarks") String remarks);
    @Select("<script>" +
            "SELECT professinfo.type, professinfo.proCode," +
            "       school.schoolName," +
            "       professinfo.professName," +
            "       school.city," +
            "       MAX(CASE WHEN year = 2021 THEN minScore ELSE NULL END) AS 'twoYearBefore', MAX(CASE WHEN year = 2022 THEN minScore ELSE NULL END) AS 'oneYearBefore', MAX(CASE WHEN year = 2023 THEN minScore ELSE NULL END) AS 'currentYear', professinfo.remarks " +
            "FROM school, professinfo, yearsocre " +
            "WHERE yearsocre.proId = professinfo.proId " +
            "  AND yearsocre.schoolId = school.schoolId " +
            "<if test='schoolName != null and schoolName.length>0'>AND school.schoolName LIKE CONCAT('%', #{schoolName}, '%')</if>" +
            "GROUP BY professinfo.type, professinfo.proCode, school.schoolName, professinfo.professName, school.city, professinfo.remarks " +
            "LIMIT #{current}, #{pageSize}" +
            "</script>")
    List<ScoreAndProfessinfoVo> getScoreBySchoolName(@Param("schoolName") String schoolName, @Param("current") int current, @Param("pageSize") int pageSize);

    /**
     * 专业名模糊查询信息
     */
    @Select("<script>" +
            "SELECT professinfo.type, professinfo.proCode," +
            "       school.schoolName," +
            "       professinfo.professName," +
            "       school.city," +
            "       MAX(CASE WHEN year = 2021 THEN minScore ELSE NULL END) AS 'twoYearBefore', MAX(CASE WHEN year = 2022 THEN minScore ELSE NULL END) AS 'oneYearBefore', MAX(CASE WHEN year = 2023 THEN minScore ELSE NULL END) AS 'currentYear', professinfo.remarks " +
            "FROM school, professinfo, yearsocre " +
            "WHERE yearsocre.proId = professinfo.proId " +
            "  AND yearsocre.schoolId = school.schoolId " +
            "<if test='professName != null and professName.length>0'>AND professinfo.professName LIKE CONCAT('%', #{professName}, '%')</if>" +
            "GROUP BY professinfo.type, professinfo.proCode, school.schoolName, professinfo.professName, school.city, professinfo.remarks " +
            "LIMIT #{current}, #{pageSize}" +
            "</script>")
    List<ScoreAndProfessinfoVo> getScoreByProfessinfo(@Param("professName") String professName, @Param("current") int current, @Param("pageSize") int pageSize);

    /**
     * 获取分数
     * @param remarks
     * @return
     */

    @Select("SELECT professinfo.type, professinfo.proCode," +
            "       school.schoolName," +
            "       professinfo.professName," +
            "       school.city," +
            "       MAX(CASE WHEN year = 2021 THEN minScore ELSE NULL END) AS 'twoYearBefore', MAX(CASE WHEN year = 2022 THEN minScore ELSE NULL END) AS 'oneYearBefore', MAX(CASE WHEN year = 2023 THEN minScore ELSE NULL END) AS 'currentYear', professinfo.remarks " +
            "FROM school, professinfo, yearsocre " +
            "WHERE yearsocre.proId = professinfo.proId " +
            "  AND yearsocre.schoolId = school.schoolId " +
            "  AND (professinfo.remarks LIKE CONCAT('%', #{remarks}, '%') OR LENGTH(professinfo.remarks) <= 51) \n" +
            "GROUP BY professinfo.type, professinfo.proCode, school.schoolName, professinfo.professName, school.city, professinfo.remarks " +
            "LIMIT #{current}, #{pageSize}" )
    List<ScoreAndProfessinfoVo> getScoreByRemarks(@Param("remarks") String remarks,@Param("current") int current, @Param("pageSize") int pageSize);


}




