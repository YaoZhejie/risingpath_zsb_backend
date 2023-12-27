package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.domain.dto.AddYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.dto.PutYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ScoreAndProfessinfoVo;
import com.yzj.risingpath_zsb_backend.domain.vo.YearScoreVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.mapper.ProfessinfoMapper;
import com.yzj.risingpath_zsb_backend.mapper.SchoolMapper;
import com.yzj.risingpath_zsb_backend.mapper.YearsocreMapper;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 姚浙杰
 * @description 针对表【yearsocre】的数据库操作Service实现
 * @createDate 2023-06-02 16:02:48
 */
@Service
public class YearsocreServiceImpl extends ServiceImpl<YearsocreMapper, Yearsocre>
        implements YearsocreService {

    @Resource
    private YearsocreMapper yearsocreMapper;

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private ProfessinfoMapper professinfoMapper;


    /**
     * 根据专业Id查询学校信息
     *
     * @param proId
     * @return
     */
    @Override
    public List<Yearsocre> ScoreByProId(Integer proId) {
        QueryWrapper<Yearsocre> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("proId", proId);
        List<Yearsocre> list = yearsocreMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public List<YearScoreVo> getYearScoreBySchool(Integer sid, Integer proId) {
        //获取学校的id作为键，学校对象作为value
        Map<Integer, School> schoolMap = schoolMapper.selectList(null).stream().collect(Collectors.toMap(School::getSchoolId, Function.identity()));
        //获取专业的id作为键，专业的对象作为value
        Map<Integer, Professinfo> proMap = professinfoMapper.selectList(null).stream().collect(Collectors.toMap(Professinfo::getProId, Function.identity()));
        //查询对应学校专业的分数列表
        List<Yearsocre> list = this.list(new QueryWrapper<Yearsocre>().eq("schoolId", sid).eq("proId", proId));
        //创建一个List<YearScoreVo>作为输出VO
        List<YearScoreVo> resultList = new ArrayList<>();
        //将学校的名字和专业的名字赋值到VO对象当中
        list.stream().forEach(yearsocre -> {
            YearScoreVo yearScoreVo = new YearScoreVo();
            //将 yearsocre 对象的属性值复制到 yearScoreVo 对象中
            BeanUtils.copyProperties(yearsocre, yearScoreVo);
            //通过yearScoreVo中的学校id获取到学校中的学校名
            String schoolName = schoolMap.get(yearScoreVo.getSchoolId()).getSchoolName();
            String professName = proMap.get(yearScoreVo.getProId()).getProfessName();
            yearScoreVo.setProfessName(professName);
            yearScoreVo.setSchoolName(schoolName);
            //增加一个YearScoreVo到resultList
            resultList.add(yearScoreVo);
        });
        return resultList;
    }

    @Override
    public Boolean saveYearScore(AddYearScoreRequest addYearScoreRequest) {
        Yearsocre yearsocre = new Yearsocre();
        BeanUtils.copyProperties(addYearScoreRequest, yearsocre);
        yearsocre.setYear(addYearScoreRequest.getScoreYear());
        //判断学校，专业是否存在
        School schoolId = schoolMapper.selectOne(new QueryWrapper<School>().eq("schoolName", addYearScoreRequest.getSchoolName()));
        if (schoolId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Professinfo professinfo = professinfoMapper.selectOne(new QueryWrapper<Professinfo>().eq("professName", addYearScoreRequest.getProfessName()).eq("schoolId",schoolId.getSchoolId()));
            if (professinfo == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        yearsocre.setSchoolId(schoolId.getSchoolId());
        yearsocre.setProId(professinfo.getProId());
        return this.save(yearsocre);

    }

    @Override
    public Boolean updateYearScore(PutYearScoreRequest putYearScoreRequest) {
        Yearsocre yearsocre = new Yearsocre();
        BeanUtils.copyProperties(putYearScoreRequest, yearsocre);
        //判断学校，专业是否存在
        School schoolId = schoolMapper.selectOne(new QueryWrapper<School>().eq("schoolName", putYearScoreRequest.getSchoolName()));
        if (schoolId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Professinfo professinfo = professinfoMapper.selectOne(new QueryWrapper<Professinfo>().eq("professName", putYearScoreRequest.getProfessName()).eq("schoolId",schoolId.getSchoolId()));
        if (professinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        yearsocre.setSchoolId(schoolId.getSchoolId());
        yearsocre.setProId(professinfo.getProId());
        return this.updateById(yearsocre);
    }

    @Override
    public List<ScoreAndProfessinfoVo> getScoreBySchoolName(String schoolName,Integer current, Integer pageSize) {
        return yearsocreMapper.getScoreBySchoolName(schoolName,current,pageSize);
    }

    @Override
    public List<ScoreAndProfessinfoVo> getScoreByProfessinfo(String professName,Integer current, Integer pageSize) {
        return yearsocreMapper.getScoreByProfessinfo(professName,current,pageSize);
    }

    @Override
    public List<ScoreAndProfessinfoVo> getScoreByRemarks(String remarks,Integer current, Integer pageSize) {
        return yearsocreMapper.getScoreByRemarks(remarks,current,pageSize);
    }

    @Override
    public Integer selectScoreBySchoolNameCount(String schoolName) {
        return this.baseMapper.selectScoreBySchoolNameCount(schoolName);
    }

    @Override
    public Integer selectScoreByProfessNameCount(String professName) {
        return this.baseMapper.selectScoreByProfessNameCount(professName);
    }

    @Override
    public Integer selectSocreByRemarkCount(String remarks) {
        return this.baseMapper.selectSocreByRemarkCount(remarks);
    }


}




