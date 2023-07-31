package com.yzj.risingpath_zsb_backend.service;

import com.yzj.risingpath_zsb_backend.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 姚浙杰
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-28 13:20:29
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String phone,String professInfo,String userName);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

     /**
     根据主键查询一条记录
      */
    User selectById(Long id);

    /**
     * 更新用户信息
     */
    int updateUserMsg(User user);

    /**
     * 用户修改密码
     */
    int updatePassword(String password ,String checkPassword ,Long id);
}
