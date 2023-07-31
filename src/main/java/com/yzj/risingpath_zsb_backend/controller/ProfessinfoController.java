package com.yzj.risingpath_zsb_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import com.yzj.risingpath_zsb_backend.domain.Yearsocre;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.service.ProfessinfoService;
import com.yzj.risingpath_zsb_backend.service.YearsocreService;
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

    /**
     * 查询所有专业信息,添加redis缓存
     */
    @RequestMapping(value = "/allprofessinfo", method = RequestMethod.GET)
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
     * 根据专业模糊查询
     */
    @RequestMapping(value = "/professinfoBykey", method = RequestMethod.GET)
    public List<Professinfo> professinfoBykey(HttpServletRequest req) {
        String professinfoName = req.getParameter("professinfo").trim();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取模糊查询的值
        List<Professinfo> list = (List<Professinfo>) valueOperations.get("pName_" + professinfoName);
        //从redis获取菜单数据，如果为空，从数据库获取
        if (CollectionUtils.isEmpty(list)) {
            list = professinfoService.professinfoLikeKey(professinfoName);
            valueOperations.set("pName_" + professinfoName, list);
        }
        return list;
    }

    /**
     * 根据学校名称模糊查询
     */
    @RequestMapping(value = "/professinfoLikeSchoolName", method = RequestMethod.GET)
    public List<Professinfo> professinfoLikeSchoolName(HttpServletRequest req) {
        String schoolName = req.getParameter("schoolName").trim();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取学校名称模糊查询的值
        List<Professinfo> list = (List<Professinfo>) valueOperations.get("pSName" + schoolName);
        //从redis获取菜单数据，如果为空，从数据库获取
        if (CollectionUtils.isEmpty(list)) {
            list = professinfoService.professLikeSchoolName(schoolName);
            valueOperations.set("pSName" + schoolName, list);
        }
        return list;
    }

    /**
     * 根据备注模糊查询
     */

    @RequestMapping(value = "/professinfoByRemark", method = RequestMethod.GET)
    public List<Professinfo> professinfoByRemark(HttpServletRequest req) {
        String remark = req.getParameter("remark").trim();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        List<Professinfo> list = (List<Professinfo>) valueOperations.get("pRemark"+ remark);
        //从redis获取菜单数据，如果为空，从数据库获取
        if (CollectionUtils.isEmpty(list)){
            list = professinfoService.professLikeRemark(remark);
            valueOperations.set("pRemark"+remark,list);
        }
        return list;
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
