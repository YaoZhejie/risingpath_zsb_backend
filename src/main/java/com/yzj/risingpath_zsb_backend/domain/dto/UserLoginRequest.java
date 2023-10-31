package com.yzj.risingpath_zsb_backend.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author 姚浙杰
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    @NotNull
    @NotBlank
    private String userAccount;

    @NotNull
    @NotBlank
    private String userPassword;
}
