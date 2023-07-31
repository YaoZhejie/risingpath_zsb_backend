package com.yzj.risingpath_zsb_backend.controller;

import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Learn;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.service.LearnService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/learn")

public class LearnController {

    @Resource
    private LearnService learnService;

    @GetMapping("/allLearn")
    @Cacheable("allLearnCache")
    public List<Learn> allLearn() {
        return learnService.list();
    }

    /**
     * 删除学习链接
     * @param learn
     * @param request
     * @return
     */
    @PostMapping(value = "/delete")
    public BaseResponse<Boolean> deleteLearn(@RequestBody Learn learn ,HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        int id = learn.getLearnId();
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = learnService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 增加学习链接
     *
     * @param learn
     * @return
     */
    @PostMapping(value = "/insert")
    public BaseResponse<Boolean> learnInsert(@RequestBody Learn learn, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (learn == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean result = learnService.save(learn);
        return ResultUtils.success(result);
    }

    /**
     * 修改学习资料信息
     * @param learn
     * @param request
     * @return
     */
    @PostMapping(value="/update")
    public BaseResponse<Boolean> updateLearn(@RequestBody Learn learn ,HttpServletRequest request){
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (learn == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = learnService.updateById(learn);
        return  ResultUtils.success(result);
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
