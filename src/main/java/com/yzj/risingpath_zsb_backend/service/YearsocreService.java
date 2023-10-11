package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzj.risingpath_zsb_backend.domain.dto.AddYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.dto.PutYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.YearScoreVo;
import com.yzj.risingpath_zsb_backend.domain.dto.YearScoreRequest;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【yearsocre】的数据库操作Service
* @createDate 2023-06-02 16:02:48
*/
public interface YearsocreService extends IService<Yearsocre> {

    /**
     * 返回近三年分数线
     * @return
     */
    public List<YearScoreRequest> allYearScore();

    /**
     * 根据专业Id查询学校分数
     */
    public List<Yearsocre> ScoreByProId(Integer proId);


    List<YearScoreVo> getYearScoreBySchool(Integer sid, Integer proId);

   Boolean saveYearScore(AddYearScoreRequest addYearScoreRequest);

   Boolean updateYearScore(PutYearScoreRequest putYearScoreRequest);
}
