package com.yzj.risingpath_zsb_backend.volunteerStrategy;

import com.yzj.risingpath_zsb_backend.domain.vo.SmartVolunteerVo;

import java.util.List;

public interface SelectStrategy {
    List<SmartVolunteerVo> getSmartListVo(List<SmartVolunteerVo> keyList, List<SmartVolunteerVo> otherList,String type);
}
