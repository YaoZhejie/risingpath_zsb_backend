package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.Collect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yzj.risingpath_zsb_backend.domain.dto.PutCollectRequest;

import java.util.List;

/**
 * @author 姚浙杰
 * @description 针对表【collect】的数据库操作Service
 * @createDate 2023-10-20 10:30:25
 */
public interface CollectService extends IService<Collect> {

    Boolean saveCollect(PutCollectRequest putCollectRequest);

    List<Collect> selectCollectListByUserId(Long userId);

    Boolean deleteCollect(Integer schoolId, Integer proId);
}
