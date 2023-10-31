package com.yzj.risingpath_zsb_backend.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PasswordRequest implements Serializable {
    private static final long serialVersionUID = 314561716373120793L;

    @TableId(type = IdType.AUTO)
    private Long userId;

    @NotNull
    private String UserPassword;

    @NotNull
    private String CheckPassword;
}
