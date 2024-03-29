package com.yzj.risingpath_zsb_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.domain.dto.ProfessionInfoRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ProfessionAndSchoolVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;


@RestController
@RequestMapping("/professinfo")

public class ProfessinfoController {

    @Resource
    private ProfessinfoService professinfoService;

    @Resource
    private YearsocreService yearsocreService;


    @Autowired
    private RedisTemplate redisTemplate;


    @ApiOperation("学校名模糊查询分页")
    @PostMapping("/likeSchoolNameByPage")
    public BaseResponse<Page<ProfessionAndSchoolVo>> likeSchoolNameByPage(@RequestBody ProfessionInfoRequest professionInfoRequest) {
        String schoolName = professionInfoRequest.getSchoolName();
        int pageSize = professionInfoRequest.getPageSize();
        int current = (professionInfoRequest.getCurrent() - 1) * pageSize;
        Integer total = professinfoService.selectProfessInfoBySchoolNameCount(schoolName);
        List<ProfessionAndSchoolVo> List = professinfoService.selectProfessInfoBySchoolName(schoolName, current, pageSize);
        Page<ProfessionAndSchoolVo> page = new Page<>(professionInfoRequest.getCurrent(), professionInfoRequest.getPageSize(), total);
        page.setRecords(List);
        return ResultUtils.success(page);
    }

    @ApiOperation("专业名模糊查询")
    @PostMapping("/likeProfessinfo")
    public BaseResponse<Page<ProfessionAndSchoolVo>> likeProfession(@RequestBody ProfessionInfoRequest professionInfoRequest) {
        String professionName = professionInfoRequest.getProfessName();
        int pageSize = professionInfoRequest.getPageSize();
        int current = (professionInfoRequest.getCurrent() - 1) * pageSize;
        int total = professinfoService.selectProfessInfoByProNameCount(professionName);
        List<ProfessionAndSchoolVo> List = professinfoService.selectProfessInfoByPro(professionName, current, pageSize);
        Page<ProfessionAndSchoolVo> page = new Page<>(professionInfoRequest.getCurrent(), professionInfoRequest.getPageSize(), total);
        page.setRecords(List);
        return ResultUtils.success(page);
    }

    @ApiOperation("备注名模糊查询")
    @PostMapping("/likeRemarks")
    public BaseResponse<Page<ProfessionAndSchoolVo>> likeRemarks(@RequestBody ProfessionInfoRequest professionInfoRequest) {
        String remarks = professionInfoRequest.getRemarks();
        int pageSize = professionInfoRequest.getPageSize();
        int current = (professionInfoRequest.getCurrent() - 1) * pageSize;
        int total = professinfoService.selectProfessInfoByRemarkCount(remarks);
        List<ProfessionAndSchoolVo> list = professinfoService.selectProfessInfoByRemarks(remarks, current, pageSize);
        Page<ProfessionAndSchoolVo> page = new Page<>(professionInfoRequest.getCurrent(), professionInfoRequest.getPageSize(), total);
        page.setRecords(list);
        return ResultUtils.success(page);
    }



    /**
     * 修改专业信息
     */
    @ApiOperation("修改专业信息")
    @PostMapping(value = "/update")
    public BaseResponse<Boolean> updateProfess(@RequestBody Professinfo professinfo, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (professinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        System.out.println(professinfo.getProId());
        Boolean b = professinfoService.updateById(professinfo);
        return ResultUtils.success(b);
    }

    /**
     * 增加学校招收专业信息
     */
    @ApiOperation("增加学校招收专业信息")
    @PostMapping(value = "/insert")
    public BaseResponse<Boolean> insertProfess(@RequestBody Professinfo professinfo, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (professinfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = professinfoService.save(professinfo);
        return ResultUtils.success(b);
    }


    /**
     * 删除专业信息以及其所在的分数线
     */
    @ApiOperation("删除专业信息以及其所在的分数线")
    @PostMapping(value = "/delete/{proId}")
    public BaseResponse<Boolean> deleteProfessinfo(@PathVariable Integer proId, HttpServletRequest request) {
        //验证是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //删除专业
        Boolean flagProDelete = professinfoService.removeById(proId);
        //获取和专业Id相同的分数线信息
        List<Yearsocre> yearsocreList = yearsocreService.ScoreByProId(proId);
        //遍历分数id删除分数
        for (Yearsocre yearscore : yearsocreList) {
            int scoreId = yearscore.getScoreId();
            Boolean yearScoreDelete = yearsocreService.removeById(scoreId);
        }
        return ResultUtils.success(flagProDelete);
    }

    @ApiOperation("通过学校id查询专业")
    @GetMapping("/selectProfessionBySchoolId/{sid}")
    public BaseResponse<List<Professinfo>> selectProfessionBySchoolId(@PathVariable Integer sid) {
        QueryWrapper<Professinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolId", sid);
        List<Professinfo> list = professinfoService.list(queryWrapper);
        if (list == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(list);
    }


    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }


}
