package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ExcelUtil;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Collect;
import com.yzj.risingpath_zsb_backend.domain.dto.DeleteCollectRequest;
import com.yzj.risingpath_zsb_backend.domain.dto.PutCollectRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.CollectOutput;
import com.yzj.risingpath_zsb_backend.domain.vo.ProfessionAndSchoolVo;
import com.yzj.risingpath_zsb_backend.domain.vo.SimVolunteerOutput;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.CollectService;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @Autowired
    private ProfessinfoService professinfoService;

    @ApiOperation("添加收藏")
    @PostMapping("/addCollect")
    public BaseResponse<Boolean> saveCollect(@Validated @RequestBody PutCollectRequest putCollectRequest) {
        return ResultUtils.success(collectService.saveCollect(putCollectRequest));
    }

    @ApiOperation("删除收藏")
    @PostMapping("/deleteCollect")
    public BaseResponse<Boolean> deleteCollect(@Validated @RequestBody DeleteCollectRequest deleteCollectRequest) {
        Integer schoolId = deleteCollectRequest.getSchoolId();
        Integer proId = deleteCollectRequest.getProId();
        if (schoolId== null || proId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean flag = collectService.deleteCollect(schoolId,proId);
        return ResultUtils.success(flag);
    }

    //根据用户id获取收藏列表
    public List<Collect> selectCollectListByUserId(Long userId) {
        List<Collect> collectList = collectService.selectCollectListByUserId(userId);
        return collectList;
    }

    @ApiOperation("根据查找出来的收藏列表获取数据")
    @PostMapping("/selectCollectInfoByUserId")
    public BaseResponse<List<ProfessionAndSchoolVo>> selectCollectInfoByUserId(Long userId){
        if (userId == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取收藏列表
        List<Collect> collectList = selectCollectListByUserId(userId);
        List<ProfessionAndSchoolVo> list = new ArrayList<>();
        collectList.forEach(collect -> {
            Integer proId = collect.getProId();
            Integer schoolId = collect.getSchoolId();
            ProfessionAndSchoolVo professionAndSchoolVo = professinfoService.selectProfessInfo(proId,schoolId);
            list.add(professionAndSchoolVo);
        });
        return ResultUtils.success(list);
    }

    @ApiOperation("导出志愿收藏")
    @GetMapping("/generateCollect/{userId}")
    public void generateCollect(@PathVariable Long userId, HttpServletResponse response){
        if (userId == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取收藏列表
        List<Collect> collectList = selectCollectListByUserId(userId);
        List<CollectOutput> CollectOutputs = new ArrayList<>();
        collectList.forEach(collect -> {
            Integer proId = collect.getProId();
            Integer schoolId = collect.getSchoolId();
            CollectOutput collectOutput = professinfoService.selectGenerateInfo(proId,schoolId);
            CollectOutputs.add(collectOutput);
        });
        String fileName = "志愿收藏.xls";
        try {
            ExcelUtil.process(fileName,CollectOutputs, CollectOutput.class,response);
        } catch (IOException e) {
            log.info("导出志愿收藏失败!");
        }
    }
}
