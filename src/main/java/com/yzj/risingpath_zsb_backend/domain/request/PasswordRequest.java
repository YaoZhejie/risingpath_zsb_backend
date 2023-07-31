package com.yzj.risingpath_zsb_backend.domain.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordRequest implements Serializable {
    private static final long serialVersionUID = 314561716373120793L;

    @TableId(type = IdType.AUTO )
    private Long userId;

    private String UserPassword;

    private String CheckPassword;
}
