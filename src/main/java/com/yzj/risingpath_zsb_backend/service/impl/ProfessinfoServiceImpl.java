package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.vo.*;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.mapper.ProfessinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author 姚浙杰
 * @description 针对表【professinfo】的数据库操作Service实现
 * @createDate 2023-05-31 10:50:53
 */
@Service
public class ProfessinfoServiceImpl extends ServiceImpl<ProfessinfoMapper, Professinfo>
        implements ProfessinfoService {

    @Resource
    private ProfessinfoMapper professinfoMapper;

    /**
     * 根据学校id查询专业信息
     *
     * @param schoolId
     * @return
     */
    @Override
    public List<Professinfo> professBySchoolId(Integer schoolId) {
        QueryWrapper<Professinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolId", schoolId);
        return professinfoMapper.selectList(queryWrapper);
    }

    @Override
    public int countDistinctProfession() {
        return professinfoMapper.countDistinctProfession();
    }

    @Override
    public int CountAllTotalPlan() {
        return professinfoMapper.CountAllTotalPlan();
    }

    @Override
    public int SumPublicProfession() {
        return professinfoMapper.SumPublicProfession();
    }

    @Override
    public int SumPrivateProfession() {
        return professinfoMapper.SumPrivateProfession();
    }

    @Override
    public List<ProfessionTypeVo> getCountOfMajorsByType() {
        return professinfoMapper.getCountOfMajorsByType();
    }

    @Override
    public List<ProfessionAndSchoolVo> selectProfessInfoBySchoolName(String schoolName, Integer current, Integer pageSize) {
        return professinfoMapper.selectProfessInfoBySchoolName(schoolName, current, pageSize);
    }

    @Override
    public Integer selectProfessInfoBySchoolNameCount(String schoolName) {
        return this.baseMapper.selectProfessInfoBySchoolNameCount(schoolName);
    }

    @Override
    public List<ProfessionAndSchoolVo> selectProfessInfoByPro(String professName, Integer current, Integer pageSize) {
        return professinfoMapper.selectProfessInfoByPro(professName, current, pageSize);
    }

    @Override
    public List<ProfessionAndSchoolVo> selectProfessInfoByRemarks(String remarks, Integer current, Integer pageSize) {
        return professinfoMapper.selectProfessInfoByRemarks(remarks, current, pageSize);
    }

    @Override
    public ProfessionAndSchoolVo selectProfessInfo(Integer proId, Integer schoolId) {
        return professinfoMapper.selectProfessInfo(proId, schoolId);
    }

    @Override
    public CollectOutput selectGenerateInfo(Integer proId, Integer schoolId) {
        return professinfoMapper.selectGenerateInfo(proId, schoolId);
    }

    /**
     * 获取对口一本志愿
     */
    @Override
    public List<SmartVolunteerVo> getKeySchoolVolunteersMajor(String remarks) {
        return professinfoMapper.getKeySchoolVolunteersMajor(remarks);
    }

    /**
     * 获取对口其他志愿
     */
    @Override
    public List<SmartVolunteerVo> getPublicSchoolVolunteersMajor(String remarks) {
        return professinfoMapper.getPublicSchoolVolunteersMajor(remarks);
    }

    @Override
    public List<SmartVolunteerVo> getKeySchoolVolunteersNoMajor(String remarks) {
        return professinfoMapper.getKeySchoolVolunteersNoMajor(remarks);
    }

    @Override
    public List<SmartVolunteerVo> getPublicSchoolVolunteersNoMajor(String remarks) {
        return professinfoMapper.getPublicSchoolVolunteersNoMajor(remarks);
    }

    @Override
    public List<SimulationMajorVo> getSimulationMajorVo(@Param("remarks") String remarks, @Param("school") String schoolName, @Param("type") String type) {
        return professinfoMapper.getSimulationMajorVo(remarks, schoolName, type);
    }

    @Override
    public int selectProfessInfoByProNameCount(String professionName) {
        return this.baseMapper.selectProfessInfoByProNameCount(professionName);
    }

    @Override
    public int selectProfessInfoByRemarkCount(String remarks) {
        return this.baseMapper.selectProfessInfoByRemarkCount(remarks);
    }


}




