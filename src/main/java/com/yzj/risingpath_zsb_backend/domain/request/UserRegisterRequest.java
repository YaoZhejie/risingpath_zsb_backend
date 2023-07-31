package com.yzj.risingpath_zsb_backend.domain.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author 姚浙杰
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @TableId(type = IdType.AUTO )
    private Long userId;

    private String userName;

    private String professInfo;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String phone;

}
