package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzj.risingpath_zsb_backend.domain.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 姚浙杰
 * @description 针对表【professinfo】的数据库操作Mapper
 * @createDate 2023-05-31 10:50:53
 * @Entity com.yzj.risingpath_zsb_backend.domain.Professinfo
 */
public interface ProfessinfoMapper extends BaseMapper<Professinfo> {

    /**
     * 不重复的专业的数量
     *
     * @return
     */
    @Select("SELECT COUNT(DISTINCT professName) FROM professinfo")
    int countDistinctProfession();

    /**
     * 招生的所有人
     */
    @Select("SELECT SUM(totalPlan) FROM professinfo")
    int CountAllTotalPlan();

    /**
     * 公办专业总人数
     *
     * @return
     */
    @Select("SELECT SUM(totalPlan) \n" +
            "FROM school,professinfo\n" +
            " WHERE school.schoolId = professinfo.schoolId\n" +
            " and school.schoolPhone = '公办'")
    int SumPublicProfession();

    /**
     * 民办专业总人数
     *
     * @return
     */
    @Select("SELECT SUM(totalPlan) \n" +
            "FROM school,professinfo\n" +
            " WHERE school.schoolId = professinfo.schoolId\n" +
            " and school.schoolPhone = '民办'")
    int SumPrivateProfession();

    /**
     * 各类专业数
     */
    @Select("SELECT  type,COUNT(*) AS professinfoNumber FROM professinfo GROUP BY type")
    List<ProfessionTypeVo> getCountOfMajorsByType();


    @Select("<script>" +
            "SELECT professinfo.type,professinfo.schoolId,professinfo.proCode,professinfo.proId, school.schoolCode, school.schoolName, " +
            "professinfo.professName, professinfo.totalPlan, professinfo.troublePlan, professinfo.soldierPlan, " +
            "professinfo.tuition, professinfo.englishReq, professinfo.remarks " +
            "FROM professinfo, school " +
            "WHERE professinfo.schoolId = school.schoolId " +
            "<if test='schoolName != null and schoolName.length>0'>AND school.schoolName LIKE CONCAT('%', #{schoolName}, '%')</if>" +
            "</script>")
    List<ProfessionAndSchoolVo> selectProfessInfoBySchoolName(@Param("schoolName") String schoolName);

    @Select("<script>" +
            "SELECT professinfo.type, professinfo.schoolId,professinfo.proCode,professinfo.proId, school.schoolCode, school.schoolName, " +
            "professinfo.professName, professinfo.totalPlan, professinfo.troublePlan, professinfo.soldierPlan, " +
            "professinfo.tuition, professinfo.englishReq, professinfo.remarks " +
            "FROM professinfo, school " +
            "WHERE professinfo.schoolId = school.schoolId " +
            "<if test='professName != null and professName.length>0'>AND professinfo.professName LIKE CONCAT('%', #{professName}, '%')</if>" +
            "</script>")
    List<ProfessionAndSchoolVo> selectProfessInfoByPro(@Param("professName") String professName);

    @Select("SELECT DISTINCT professinfo.type,professinfo.schoolId,professinfo.proCode, professinfo.proId, school.schoolCode, school.schoolName, " +
            "professinfo.professName, professinfo.totalPlan, professinfo.troublePlan, professinfo.soldierPlan, " +
            "professinfo.tuition, professinfo.englishReq, professinfo.remarks " +
            "FROM professinfo, school " +
            "WHERE professinfo.schoolId = school.schoolId " +
            "AND (professinfo.remarks LIKE CONCAT('%', #{remarks}, '%') OR LENGTH(professinfo.remarks) <= 51 )")
    List<ProfessionAndSchoolVo> selectProfessInfoByRemarks(@Param("remarks") String remarks);

    /**
     * 根据学校id，专业id查询一个专业的数据
     */
    @Select("SELECT professinfo.type, professinfo.schoolId,professinfo.proCode, professinfo.proId, school.schoolCode, school.schoolName, professinfo.professName, professinfo.totalPlan, professinfo.troublePlan, professinfo.soldierPlan, professinfo.tuition, professinfo.englishReq, professinfo.remarks\n" +
            "FROM professinfo,school\n" +
            "WHERE professinfo.schoolId = school.schoolId \n" +
            "AND professinfo.proId=#{proId} AND school.schoolId=#{schoolId}")
    ProfessionAndSchoolVo selectProfessInfo(@Param("proId") Integer proId, @Param("schoolId") Integer schoolId);

    /**
     * 生成志愿
     */
    @Select("SELECT professinfo.type,professinfo.proCode, school.schoolCode, school.schoolName, professinfo.professName, professinfo.totalPlan, professinfo.troublePlan, professinfo.soldierPlan, professinfo.tuition, professinfo.englishReq, professinfo.remarks\n" +
            "FROM professinfo,school\n" +
            "WHERE professinfo.schoolId = school.schoolId \n" +
            "AND professinfo.proId=#{proId} AND school.schoolId=#{schoolId}")
    CollectOutput selectGenerateInfo(@Param("proId") Integer proId, @Param("schoolId") Integer schoolId);

    /**
     * 对口专业获取一本院校志愿
     */
    @Select("SELECT p.type,p.proCode,p.professName,s.schoolCode,s.schoolName,y.minScore\n" +
            "FROM professinfo as p\n" +
            "JOIN school AS s ON p.schoolId = s.schoolId\n" +
            "JOIN yearsocre AS y ON p.proId = y.proId\n" +
            "WHERE y.`year` = '2023' AND y.minScore > 231 AND p.remarks LIKE CONCAT('%', #{remarks}, '%')\n" +
            "ORDER BY y.minScore ASC")
    List<SmartVolunteerVo> getKeySchoolVolunteersMajor(@Param("remarks") String remarks);

    /**
     * 对口专业获取其他
     */
    @Select("SELECT p.type,p.proCode,p.professName,s.schoolCode,s.schoolName,y.minScore\n" +
            "FROM professinfo as p\n" +
            "JOIN school AS s ON p.schoolId = s.schoolId\n" +
            "JOIN yearsocre AS y ON p.proId = y.proId\n" +
            "WHERE y.`year` = '2023' AND y.minScore <=249 AND p.remarks LIKE CONCAT('%', #{remarks}, '%')\n" +
            "ORDER BY y.minScore DESC")
    List<SmartVolunteerVo> getPublicSchoolVolunteersMajor(@Param("remarks") String remarks);

    /**
     * 非对口一本志愿填报
     */
    @Select("SELECT DISTINCT p.type,p.proCode,p.professName,s.schoolCode,s.schoolName,y.minScore\n" +
            "FROM professinfo as p \n" +
            "JOIN school AS s ON p.schoolId = s.schoolId \n" +
            "JOIN yearsocre AS y ON p.proId = y.proId\n" +
            "WHERE y.`year` = '2023'  AND p.remarks LIKE CONCAT('%', #{remarks}, '%')" +
            " OR LENGTH(p.remarks) <= 51 AND y.minScore > 231\n" +
            "ORDER BY y.minScore ASC\n")
    List<SmartVolunteerVo> getKeySchoolVolunteersNoMajor(@Param("remarks") String remarks);

    /**
     * 非对口普通填报
     */
    @Select("SELECT DISTINCT p.type,p.proCode,p.professName,s.schoolCode,s.schoolName,y.minScore\n" +
            "FROM professinfo as p \n" +
            "JOIN school AS s ON p.schoolId = s.schoolId \n" +
            "JOIN yearsocre AS y ON p.proId = y.proId\n" +
            "WHERE y.`year` = '2023'  AND p.remarks LIKE CONCAT('%', #{remarks}, '%')" +
            " OR LENGTH(p.remarks) <= 51 AND y.minScore <= 249\n" +
            "ORDER BY y.minScore ASC\n")
    List<SmartVolunteerVo> getPublicSchoolVolunteersNoMajor(@Param("remarks") String remarks);

    @Select("SELECT DISTINCT professinfo.proCode,professinfo.professName " +
            "FROM professinfo, school " +
            "WHERE professinfo.schoolId = school.schoolId AND (school.schoolName = #{school}) AND (professinfo.type = #{type})" +
            "AND (professinfo.remarks LIKE CONCAT('%', #{remarks}, '%')  OR LENGTH(professinfo.remarks) <= 51 )")
    List<SimulationMajorVo> getSimulationMajorVo(@Param("remarks") String remarks, @Param("school") String schoolName, @Param("type") String type);
}




