package com.yzj.risingpath_zsb_backend.controller;


import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.domain.Letterbox;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.service.LetterboxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/letterbox")

public class LetterboxController {

    @Resource
    private LetterboxService letterboxService;

    @PostMapping(value = "/insert")
    public BaseResponse<Boolean> letterInsert(@RequestBody Letterbox letterbox) {
        //验证参数是否为空
        if (letterbox == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        Date publishTime = Date.from(instant);
        //获取参数
        String title = letterbox.getTitle();
        String content = letterbox.getContent();
        if (StringUtils.isAnyBlank(title, content)) {
            return null;
        }
        boolean result = letterboxService.insertLetter(title, content, publishTime);
        return ResultUtils.success(result);
    }

    /**
     * 删除信件（只有管理员才可以删除信件）
     *
     * @param id
     * @param request
     * @return
     */
    @PostMapping(value = "/delete/{letterId}")
    public BaseResponse<Boolean> deleteLetter(@PathVariable Integer letterId,HttpServletRequest request){
        //验证是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //删除专业
        Boolean flagProDelete = letterboxService.removeById(letterId);
        return ResultUtils.success(flagProDelete);
    }

    @GetMapping("/allLetter")
    public List<Letterbox> allLetter(HttpServletRequest request) {
        //验证是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return letterboxService.list();
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
