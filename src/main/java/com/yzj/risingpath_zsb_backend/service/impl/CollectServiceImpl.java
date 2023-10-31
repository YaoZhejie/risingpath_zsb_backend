package com.yzj.risingpath_zsb_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.domain.Collect;
import com.yzj.risingpath_zsb_backend.domain.dto.PutCollectRequest;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.CollectService;
import com.yzj.risingpath_zsb_backend.mapper.CollectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 姚浙杰
 * @description 针对表【collect】的数据库操作Service实现
 * @createDate 2023-10-20 10:30:25
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect>
        implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    /**
     * 增加收藏
     * @param putCollectRequest
     * @return
     */
    @Override
    public Boolean saveCollect(PutCollectRequest putCollectRequest) {
        Integer schoolId = putCollectRequest.getSchoolId();
        Integer proId = putCollectRequest.getProId();
        Long userId = putCollectRequest.getUserId();
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolId", schoolId).eq("proId", proId).eq("userId", userId);
        List<Collect> collectList = collectMapper.selectList(queryWrapper);
        //检测该专业是都已经被用户收藏了
        if (!collectList.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Collect collect = new Collect();
        collect.setSchoolId(schoolId);
        collect.setProId(proId);
        collect.setUserId(userId);
        int flag = collectMapper.insert(collect);
        return flag > 0;
    }

    @Override
    public  List<Collect> selectCollectListByUserId(Long userId) {
        return collectMapper.selectCollectListByUserId(userId);
    }

    @Override
    public Boolean deleteCollect(Integer schoolId, Integer proId) {
        return collectMapper.deleteCollect(schoolId,proId);
    }
}




