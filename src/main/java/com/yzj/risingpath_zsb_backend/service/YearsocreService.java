package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzj.risingpath_zsb_backend.domain.dto.AddYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.dto.PutYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ScoreAndProfessinfoVo;
import com.yzj.risingpath_zsb_backend.domain.vo.YearScoreVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【yearsocre】的数据库操作Service
* @createDate 2023-06-02 16:02:48
*/
public interface YearsocreService extends IService<Yearsocre> {



    /**
     * 根据专业Id查询学校分数
     */
     List<Yearsocre> ScoreByProId(Integer proId);


    List<YearScoreVo> getYearScoreBySchool(Integer sid, Integer proId);

    /**
     * 保存分数
     */
   Boolean saveYearScore(AddYearScoreRequest addYearScoreRequest);

    /**
     * 更新分数
     */
   Boolean updateYearScore(PutYearScoreRequest putYearScoreRequest);

    /**
     *根据学校名模糊查询分数
     */
    List<ScoreAndProfessinfoVo> getScoreBySchoolName(String schoolName,Integer current, Integer pageSize);

    /**
     * 根据专业名模糊查询分数
     */
    List<ScoreAndProfessinfoVo> getScoreByProfessinfo(String professName,Integer current, Integer pageSize);

    /**
     * 根据报考要求模糊查询分数
     */
    List<ScoreAndProfessinfoVo> getScoreByRemarks(String remarks,Integer current, Integer pageSize);

    Integer selectScoreBySchoolNameCount(@Param("schoolName") String schoolName);

    Integer selectScoreByProfessNameCount(@Param("professName") String professName);

    Integer selectSocreByRemarkCount(@Param("remarks") String remarks);
}
