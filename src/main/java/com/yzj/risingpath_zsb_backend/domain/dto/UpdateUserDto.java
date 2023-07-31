package com.yzj.risingpath_zsb_backend.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserDto {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String userName;

    /**
     * 登录账户
     */
    @NotNull(message = "账户不能为空")
    private String userAccount;

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String phone;

    /**
     * 所学专业
     */
    @NotNull(message = "所学专业不能为空")
    private String professInfo;
}
