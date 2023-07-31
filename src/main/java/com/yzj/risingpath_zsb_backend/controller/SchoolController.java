package com.yzj.risingpath_zsb_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.service.SchoolService;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/school")

public class SchoolController {

    @Resource
    private SchoolService schoolService;

    @Resource
    private ProfessinfoService professinfoService;

    @Resource
    private YearsocreService yearsocreService;

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

    /**
     * 获取所有学校列表
     *
     * @param request
     * @return
     */
    @GetMapping("/allSchool")
    public List<School> allSchool(HttpServletRequest request) {
        //判断是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return schoolService.list();
    }




    /**
     * 更新学校信息
     * @param school
     * @param request
     * @return
     */
    @PutMapping(value="/update")
    public BaseResponse<Boolean> updateSchool(@RequestBody School school , HttpServletRequest request){
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (school == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = schoolService.updateById(school);
        return  ResultUtils.success(result);
    }

    /**
     * 增加学校
     * @param school
     * @param request
     * @return
     */
    @PostMapping(value = "/insert")
    public BaseResponse<Boolean> schoolInsert(@RequestBody School school, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (school == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean result = schoolService.save(school);
        return ResultUtils.success(result);
    }

    /**
     * 删除学校
     *
     * @param school
     * @param request
     * @return
     */

    @PostMapping(value = "/delete")
    public BaseResponse<Boolean> deleteLearn(@RequestBody School school, HttpServletRequest request) {
        //验证是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        int schoolId = school.getSchoolId();
        //删除学校
        Boolean flagSchoolDelete = schoolService.removeById(schoolId);

        //获取和学校id相同的专业信息
        List<Professinfo> professinfoList = professinfoService.professBySchoolId(schoolId);
        //遍历专业id删除专业信息
        for (Professinfo professinfo : professinfoList) {              // 使用foreach循环遍历ArrayList对象
            int proId = professinfo.getProId();
            //添加专业id信息到proIdList
            Boolean flagProfessDelete = professinfoService.removeById(proId);
            //删除分数
            QueryWrapper<Yearsocre> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("proId", proId)
                    .eq("schoolId", schoolId);
            Boolean flagScoreDelete = yearsocreService.remove(queryWrapper);
        }

        return ResultUtils.success(true);
    }





}
