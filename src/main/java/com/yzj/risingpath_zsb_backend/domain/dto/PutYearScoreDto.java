package com.yzj.risingpath_zsb_backend.domain.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
@Data
public class PutYearScoreDto  implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 分数表id
     */
    @NotNull(message = "id不能为空")
    @Positive(message = "请输入正确的分数表id")
    private Integer scoreId;

    /**
     * 学校id
     */
    @NotNull(message = "id不能为空")
    @Positive(message = "请输入正确的学校表id")
    private Integer schoolId;

    private String schoolName;


    /**
     * 专业信息id
     */
    @NotNull(message = "id不能为空")
    @Positive(message = "请输入正确的专业表id")
    private Integer proId;

    private String professName;
    /**
     * 年份
     */
    @NotNull(message = "年份不能为空")
    private Integer year;

    /**
     * 录取最高分
     */
    @Max(value = 300,message = "分数必须小于300")
    @Min(value = 0,message = "分数必须大于0")
    private Integer maxScore;

    /**
     * 录取最低分
     */
    @Max(value = 300,message = "分数必须小于300")
    @Min(value = 0,message = "分数必须大于0")
    private Integer minScore;
}
