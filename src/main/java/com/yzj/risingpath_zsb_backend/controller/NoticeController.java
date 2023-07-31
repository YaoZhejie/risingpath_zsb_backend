package com.yzj.risingpath_zsb_backend.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Notice;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/notice")

public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @GetMapping("/allNotice")
    public List<Notice> allLetter() {
        return noticeService.list();
    }

    @PostMapping(value = "/delete/{noticeId}")
    public BaseResponse<Boolean> deleteNotice(@PathVariable Integer noticeId,HttpServletRequest request){
        //验证是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //删除专业
        Boolean flagProDelete = noticeService.removeById(noticeId);
        return ResultUtils.success(flagProDelete);
    }

    /**
     * 获取一条公告信息（公告只允许一条）
     * @return
     */
    @GetMapping("/getNotice")
    public Notice getNotice(){
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("LIMIT 1"); // 添加限制只查询一条数据
        return noticeService.getOne(queryWrapper);
    }

    /**
     * 增加一条公告信息
     */
    @PostMapping(value = "/insert" )
    public BaseResponse<Boolean> insertNotice(@RequestBody Notice notice){
        //验证参数是否为空
        if (notice == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = noticeService.save(notice);
        return  ResultUtils.success(b) ;
    }

    /**
     * 修改公告
     */

    @PostMapping(value="/update")
    public BaseResponse<Boolean> updateNotice(@RequestBody Notice notice , HttpServletRequest request){
        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //验证参数是否为空
        if (notice == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = noticeService.updateById(notice);
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
