package com.yzj.risingpath_zsb_backend.domain.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
public class AddYearScoreRequest implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 学校id
     */
    @NotNull(message = "学校不能为空")
    @NotBlank(message = "学校不能为空")
    private String schoolName;
    /**
     * 专业信息id
     */


    @NotNull(message = "专业不能为空")
    @NotBlank(message = "专业不能为空")
    private String professName;

    /**
     * 年份
     */
    @NotNull(message = "年份不能为空")
    private Integer scoreYear;

    /**
     * 录取最高分
     */
    @Max(value = 300, message = "分数必须小于300")
    @Min(value = 0, message = "分数必须大于0")
    private Integer maxScore;

    /**
     * 录取最低分
     */
    @Max(value = 300, message = "分数必须小于300")
    @Min(value = 0, message = "分数必须大于0")
    private Integer minScore;
}
