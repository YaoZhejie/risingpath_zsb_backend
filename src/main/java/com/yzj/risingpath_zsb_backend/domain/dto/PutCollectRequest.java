package com.yzj.risingpath_zsb_backend.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class PutCollectRequest implements Serializable {

    /**
     * 学校的id
     */
    @NotNull(message = "学校id不能为空")
    @Positive(message = "请输入正确的学校id")
    private Integer schoolId;

    /**
     * 专业的id
     */
    @NotNull(message = "专业id不能为空")
    @Positive(message = "请输入正确的专业id")
    private Integer proId;

    /**
     * 用户的id
     */
    @NotNull(message = "用户id不能为空")
    @Positive(message = "请输入正确的用户id")
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
