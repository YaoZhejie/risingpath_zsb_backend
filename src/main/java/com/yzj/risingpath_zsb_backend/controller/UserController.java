package com.yzj.risingpath_zsb_backend.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzj.risingpath_zsb_backend.common.BaseResponse;
import com.yzj.risingpath_zsb_backend.common.ErrorCode;
import com.yzj.risingpath_zsb_backend.common.ResultUtils;
import com.yzj.risingpath_zsb_backend.exception.BusinessException;
import com.yzj.risingpath_zsb_backend.domain.User;
import com.yzj.risingpath_zsb_backend.domain.request.PasswordRequest;
import com.yzj.risingpath_zsb_backend.domain.request.UserLoginRequest;
import com.yzj.risingpath_zsb_backend.domain.request.UserRegisterRequest;
import com.yzj.risingpath_zsb_backend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.ADMIN_ROLE;
import static com.yzj.risingpath_zsb_backend.contant.UserConstant.USER_LOGIN_STATE;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        //验证参数是否为空
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //获取参数
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userName = userRegisterRequest.getUserName();
        String phone = userRegisterRequest.getPhone();
        String professInfo = userRegisterRequest.getProfessInfo();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, phone, professInfo, userName);
        return ResultUtils.success(result);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/update")
    public BaseResponse<Long> userUpdate(@RequestBody User user) {
        //验证参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.updateUserMsg(user);

        return ResultUtils.success(result);
    }

    /**
     * 修改密码
     *
     * @param passwordRequest
     * @return
     */
    @PostMapping(value = "/updatePassword")
    public BaseResponse<Long> updatePassword(@RequestBody PasswordRequest passwordRequest) {

        long result = userService.updatePassword(passwordRequest.getUserPassword(), passwordRequest.getCheckPassword(), passwordRequest.getUserId());
        return ResultUtils.success(result);
    }


    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        if (user == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(user);
    }

    /**
     * 根据主键查询用户
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        String id = request.getParameter("id").trim();
        return userService.selectById(Long.parseLong(id));
    }

    /**
     * 删除用户（只有管理员才可以删除用户）
     *
     * @param
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody User user, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        long id = user.getUserId();
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //TODO 同时删除头像文件
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
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

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 模糊查询用户
     *
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("userName", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/allUser")
    public BaseResponse<List<User>> allUser(HttpServletRequest request){
        //判断是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> listUser = userService.list();
        return ResultUtils.success(listUser);
    }





}
