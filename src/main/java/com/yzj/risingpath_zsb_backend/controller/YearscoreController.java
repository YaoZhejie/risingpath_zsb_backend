package com.yzj.risingpath_zsb_backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.domain.dto.AddYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.dto.ProfessionInfoRequest;
import com.yzj.risingpath_zsb_backend.domain.dto.PutYearScoreRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ScoreAndProfessinfoVo;
import com.yzj.risingpath_zsb_backend.domain.vo.YearScoreVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/yearscore")

public class YearscoreController {

    @Resource
    private YearsocreService yearsocreService;

//    @Autowired
//    private RedisTemplate redisTemplate;

    /**
     * 根据学校名模糊查询分数
     */
    @ApiOperation("根据学校名模糊查询分数")
    @PostMapping(value = "/scoreLikeSchoolName")
    public BaseResponse<Page<ScoreAndProfessinfoVo>> ScoreLikeSchoolName(@RequestBody ProfessionInfoRequest professionInfoRequest) {
        String schoolName = professionInfoRequest.getSchoolName();
        int pageSize = professionInfoRequest.getPageSize();
        int current = (professionInfoRequest.getCurrent() - 1) * pageSize;
        Integer total = yearsocreService.selectScoreBySchoolNameCount(schoolName);
        List<ScoreAndProfessinfoVo> list = yearsocreService.getScoreBySchoolName(schoolName,current, pageSize);
        Page<ScoreAndProfessinfoVo> page = new Page<>(professionInfoRequest.getCurrent(), professionInfoRequest.getPageSize(), total);
        page.setRecords(list);
        return ResultUtils.success(page);
    }

    /**
     * 根据专业名模糊查询院校
     */
    @ApiOperation("根据专业名模糊查询分数")
    @PostMapping(value = "/scoreLikeProfessinfo")
    public BaseResponse<Page<ScoreAndProfessinfoVo>> scoreLikeProfessinfo(@RequestBody ProfessionInfoRequest professionInfoRequest) {
        String professName = professionInfoRequest.getProfessName();
        int pageSize = professionInfoRequest.getPageSize();
        int current = (professionInfoRequest.getCurrent() - 1) * pageSize;
        Integer total = yearsocreService.selectScoreByProfessNameCount(professName);
        List<ScoreAndProfessinfoVo> list = yearsocreService.getScoreByProfessinfo(professName,current,pageSize);
        Page<ScoreAndProfessinfoVo> page = new Page<>(professionInfoRequest.getCurrent(), professionInfoRequest.getPageSize(), total);
        page.setRecords(list);
        return ResultUtils.success(page);
    }

    /**
     * 根据备注名模糊查询分数
     */
    @ApiOperation("根据备注名模糊查询分数")
    @PostMapping(value = "/scoreLikeRemarks")
    public BaseResponse<Page<ScoreAndProfessinfoVo>> scoreLikeRemarks(@RequestBody ProfessionInfoRequest professionInfoRequest) {
        String remarks = professionInfoRequest.getRemarks();
        int pageSize = professionInfoRequest.getPageSize();
        int current = (professionInfoRequest.getCurrent() - 1) * pageSize;
        Integer total = yearsocreService.selectSocreByRemarkCount(remarks);
        List<ScoreAndProfessinfoVo> list = yearsocreService.getScoreByRemarks(remarks,current,pageSize);
        Page<ScoreAndProfessinfoVo> page = new Page<>(professionInfoRequest.getCurrent(), professionInfoRequest.getPageSize(), total);
        page.setRecords(list);
        return ResultUtils.success(page);
    }

    /**
     * 删除分数
     */
    @DeleteMapping("/delete")
    public Boolean deleteYearScore(@RequestBody Yearsocre yearsocre) {
        return yearsocreService.removeById(yearsocre.getScoreId());
    }

    /**
     * 增加分数选项
     */
    @PostMapping("/insert")
    public BaseResponse<Boolean> insertScore(@RequestBody Yearsocre yearsocre) {
        if (yearsocre == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        Boolean flag = yearsocreService.save(yearsocre);
        return ResultUtils.success(flag);
    }


    @GetMapping("/get/{sid}/{proId}")
    public BaseResponse<List<YearScoreVo>> getYearScoreBySchool(@PathVariable(required = false) Integer sid, @PathVariable(required = false) Integer proId) {
        return ResultUtils.success(yearsocreService.getYearScoreBySchool(sid, proId));
    }


    @PostMapping("/update")
    public BaseResponse<Boolean> updateYearScore(@Validated @RequestBody PutYearScoreRequest yearScoreDto) {
        return ResultUtils.success(yearsocreService.updateYearScore(yearScoreDto));
    }

    @PostMapping("/delete/{sid}")
    public BaseResponse<Boolean> removeYearScore(@PathVariable Integer sid) {
        Yearsocre yearsocre = yearsocreService.getById(sid);
        if (yearsocre == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(yearsocreService.removeById(sid));
    }

    //保存一条分数信息
    @PostMapping("/save")
    public BaseResponse<Boolean> saveYearScore(@Validated @RequestBody AddYearScoreRequest yearScoreDto) {
        return ResultUtils.success(yearsocreService.saveYearScore(yearScoreDto));
    }




}
