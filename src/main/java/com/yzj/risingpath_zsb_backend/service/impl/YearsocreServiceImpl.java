package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.domain.dto.AddYearScoreDto;
import com.yzj.risingpath_zsb_backend.domain.dto.PutYearScoreDto;
import com.yzj.risingpath_zsb_backend.domain.vo.YearScoreVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.mapper.ProfessinfoMapper;
import com.yzj.risingpath_zsb_backend.mapper.SchoolMapper;
import com.yzj.risingpath_zsb_backend.domain.request.YearScoreRequest;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
import com.yzj.risingpath_zsb_backend.mapper.YearsocreMapper;
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
     * 返回所有的专业分数
     *
     * @return
     */
    @Override
    public List<YearScoreRequest> allYearScore() {
        return yearsocreMapper.allYearScore();
    }


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

        Map<Integer, School> schoolMap = schoolMapper.selectList(null).stream().collect(Collectors.toMap(School::getSchoolId, Function.identity()));
        Map<Integer, Professinfo> proMap = professinfoMapper.selectList(null).stream().collect(Collectors.toMap(Professinfo::getProId, Function.identity()));

        List<Yearsocre> list = this.list(new QueryWrapper<Yearsocre>().eq("schoolId", sid).eq("proId", proId));
        List<YearScoreVo> resultList = new ArrayList<>();

        list.stream().forEach(yearsocre -> {
            YearScoreVo yearScoreVo = new YearScoreVo();
            BeanUtils.copyProperties(yearsocre, yearScoreVo);
            String schoolName = schoolMap.get(yearScoreVo.getSchoolId()).getSchoolName();
            String professName = proMap.get(yearScoreVo.getProId()).getProfessName();
            yearScoreVo.setProfessName(professName);
            yearScoreVo.setSchoolName(schoolName);
            resultList.add(yearScoreVo);
        });
        return resultList;
    }

    @Override
    public Boolean saveYearScore(AddYearScoreDto addYearScoreDto) {
        Yearsocre yearsocre = new Yearsocre();
        BeanUtils.copyProperties(addYearScoreDto, yearsocre);
        yearsocre.setYear(addYearScoreDto.getScoreYear());
        //判断学校，专业是否存在
        School schoolId = schoolMapper.selectOne(new QueryWrapper<School>().eq("schoolName", addYearScoreDto.getSchoolName()));
        if (schoolId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Professinfo professinfo = professinfoMapper.selectOne(new QueryWrapper<Professinfo>().eq("professName", addYearScoreDto.getProfessName()).eq("schoolId",schoolId.getSchoolId()));
            if (professinfo == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        yearsocre.setSchoolId(schoolId.getSchoolId());
        yearsocre.setProId(professinfo.getProId());
        return this.save(yearsocre);

    }

    @Override
    public Boolean updateYearScore(PutYearScoreDto putYearScoreDto) {
        Yearsocre yearsocre = new Yearsocre();
        BeanUtils.copyProperties(putYearScoreDto, yearsocre);
        //判断学校，专业是否存在
        School schoolId = schoolMapper.selectOne(new QueryWrapper<School>().eq("schoolName", putYearScoreDto.getSchoolName()));
        if (schoolId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        Professinfo professinfo = professinfoMapper.selectOne(new QueryWrapper<Professinfo>().eq("professName", putYearScoreDto.getProfessName()).eq("schoolId",schoolId.getSchoolId()));
        if (professinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        yearsocre.setSchoolId(schoolId.getSchoolId());
        yearsocre.setProId(professinfo.getProId());
        return this.updateById(yearsocre);
    }


}




