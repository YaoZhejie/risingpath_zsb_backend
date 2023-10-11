package com.yzj.risingpath_zsb_backend.mapper;

import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzj.risingpath_zsb_backend.domain.dto.YearScoreRequest;

import java.util.List;

/**
* @author 姚浙杰
* @description 针对表【yearsocre】的数据库操作Mapper
* @createDate 2023-06-02 16:02:48
* @Entity com.yzj.risingpath_zsb_backend.domain.Yearsocre
*/
public interface YearsocreMapper extends BaseMapper<Yearsocre> {

    public List<YearScoreRequest> allYearScore();

}




