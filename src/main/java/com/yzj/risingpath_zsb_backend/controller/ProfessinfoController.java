package com.yzj.risingpath_zsb_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.contant.CommonConstant;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.School;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.domain.dto.ProfessionInfoRequest;
import com.yzj.risingpath_zsb_backend.domain.vo.ProfessionAndSchoolVo;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.service.SchoolService;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;


@RestController
@RequestMapping("/professinfo")

public class ProfessinfoController {

    @Resource
    private ProfessinfoService professinfoService;

    @Resource
    private YearsocreService yearsocreService;

    @Resource
    private SchoolService schoolService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分页查询所有专业信息
     */
    @ApiOperation("分页查询所有专业信息")
    @GetMapping("/pageAllProfessions")
    public BaseResponse<Page<ProfessionAndSchoolVo>> PageAllProfessions(ProfessionInfoRequest professionInfoRequest, HttpServletRequest httpServletRequest) {
        Map<Integer, School> schoolMap = schoolService.list(null).stream().collect(Collectors.toMap(School::getSchoolId, Function.identity()));
        long current = professionInfoRequest.getCurrent();
        long size = professionInfoRequest.getPageSize();
        Page<Professinfo> professinfoPage = professinfoService.page(new Page<>(current, size),getQueryWrapper(professionInfoRequest));
        List<ProfessionAndSchoolVo> list = new ArrayList<>();
        professinfoPage.getRecords().stream().forEach(professinfo -> {
            ProfessionAndSchoolVo professionAndSchoolVo = new ProfessionAndSchoolVo();
            BeanUtils.copyProperties(professinfo, professionAndSchoolVo);
            Integer schoolId = professinfo.getSchoolId();
            String schoolName = schoolMap.get(schoolId).getSchoolName();
            String schoolCode = schoolMap.get(schoolId).getSchoolCode();
            professionAndSchoolVo.setSchoolName(schoolName);
            professionAndSchoolVo.setSchoolCode(schoolCode);
            list.add(professionAndSchoolVo);
        });
        Page<ProfessionAndSchoolVo> professionAndSchoolVoPage = new Page<>(current, size);
        professionAndSchoolVoPage.setRecords(list);
        return ResultUtils.success(professionAndSchoolVoPage);
    }

    /**
     * 根据专业模糊查询
     */


    /**
     * 查询所有专业信息,添加redis缓存
     */
    @GetMapping(value = "/allprofessinfo")
    public List<Professinfo> allprofessinfo(HttpServletRequest request) {
        // 获取 RedisTemplate 中的 ValueOperations
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis中获取professions的值
        List<Professinfo> list = (List<Professinfo>) valueOperations.get("professions");
        //从redis获取菜单数据，如果为空，从数据库获取
        if (CollectionUtils.isEmpty(list)) {
            list = professinfoService.allProfessinfo();
            valueOperations.set("professions", list);
        }
        return list;
    }

    /**
     * 获取查询包装类
     *
     * @param
     * @return
     */
    @ApiOperation(value = "获取查询包装类")
    private QueryWrapper<Professinfo> getQueryWrapper(ProfessionInfoRequest professionInfoRequest) {
        if (professionInfoRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }

        Professinfo tableInfoQuery = new Professinfo();
        BeanUtils.copyProperties(professionInfoRequest, tableInfoQuery);
        String sortField = professionInfoRequest.getSortField();
        String sortOrder = professionInfoRequest.getSortOrder();
        String remarks = tableInfoQuery.getRemarks();
        String profession = tableInfoQuery.getProfessName();
        // name、content 需支持模糊搜索
        tableInfoQuery.setRemarks(null);
        tableInfoQuery.setProfessName(null);

        QueryWrapper<Professinfo> queryWrapper = new QueryWrapper<>(tableInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(profession), "professName", profession);
        queryWrapper.like(StringUtils.isNotBlank(remarks),"remarks",remarks);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }



    /**
     * 修改专业信息
     */

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

    @GetMapping("/selectProfessionBySchoolId/{sid}")
    public BaseResponse<List<Professinfo>> selectProfessionBySchoolId(@PathVariable Integer sid) {
        QueryWrapper<Professinfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolId", sid);
        // select * from profession where id=#{sid}
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
