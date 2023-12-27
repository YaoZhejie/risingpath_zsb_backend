package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzj.risingpath_zsb_backend.domain.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 姚浙杰
 * @description 针对表【professinfo】的数据库操作Service
 * @createDate 2023-05-31 10:50:53
 */
public interface ProfessinfoService extends IService<Professinfo> {

    /**
     * 根据学校主键查询专业
     */
    public List<Professinfo> professBySchoolId(Integer schoolId);


    public int countDistinctProfession();

    /**
     * 计算出所有招生人数
     *
     * @return
     */
    int CountAllTotalPlan();

    /**
     * 公办招生数量
     *
     * @return
     */
    int SumPublicProfession();

    /**
     * 民办招生数量
     *
     * @return
     */
    int SumPrivateProfession();

    /**
     * 各类型专业数量
     *
     * @return
     */
    List<ProfessionTypeVo> getCountOfMajorsByType();

    /**
     * 学校名模糊查询
     *
     * @param schoolName
     * @return
     */
    List<ProfessionAndSchoolVo> selectProfessInfoBySchoolName(String schoolName, Integer current, Integer pageSize);

    /**
     * 计算数量
     */
    Integer selectProfessInfoBySchoolNameCount(String schoolName);

    /**
     * 专业名模糊查询
     *
     * @param professName
     * @return
     */
    List<ProfessionAndSchoolVo> selectProfessInfoByPro( String professName,Integer current, Integer pageSize);

    /**
     * 备注模糊查询
     */
    List<ProfessionAndSchoolVo> selectProfessInfoByRemarks( String remarks,Integer current, Integer pageSize);

    /**
     * 根据学校id，专业id查询数据
     */
    ProfessionAndSchoolVo selectProfessInfo(@Param("proId") Integer proId, @Param("schoolId") Integer schoolId);

    /**
     * 输出志愿
     */
    CollectOutput selectGenerateInfo(@Param("proId") Integer proId, @Param("schoolId") Integer schoolId);

    /**
     * 对口获取一本志愿
     */
    List<SmartVolunteerVo> getKeySchoolVolunteersMajor(@Param("remarks") String remarks);

    /**
     * 对口获取其他志愿
     */
    List<SmartVolunteerVo> getPublicSchoolVolunteersMajor(@Param("remarks") String remarks);
    /**
     * 非对口获取一本志愿
     */
    List<SmartVolunteerVo> getKeySchoolVolunteersNoMajor(@Param("remarks") String remarks);

    /**
     * 非对口获取其他志愿
     */
    List<SmartVolunteerVo> getPublicSchoolVolunteersNoMajor(@Param("remarks") String remarks);

    /**
     * 获取模拟填报志愿的代码和专业
     */
    List<SimulationMajorVo> getSimulationMajorVo(@Param("remarks") String remarks, @Param("school") String schoolName, @Param("type") String type);


    int selectProfessInfoByProNameCount(String professionName);

    int selectProfessInfoByRemarkCount(String remarks);
}
