package com.yzj.risingpath_zsb_backend.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SmartVolunteerVo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 319145526373120793L;
    /**
     * 学校
     */
    private String schoolName;
    /**
     * 志愿类型
     */
    private String type;
    /**
     * 学校代码
     */
    private String schoolCode;

    /**
     * 专业代码
     */
    private String proCode;

    /**
     * 专业名称
     */
    private String professName;

    /**
     * 分数
     */
    private Integer minScore;
}
