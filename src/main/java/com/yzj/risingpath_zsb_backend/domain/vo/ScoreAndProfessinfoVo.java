package com.yzj.risingpath_zsb_backend.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ScoreAndProfessinfoVo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 319145526373120793L;

    private  String type;

    private String schoolName;

    private String professName;

    private String city;

    private String proCode;
    /**
     * 前年分数线
     */
    private int twoYearBefore;

    /**
     * 去年分数线
     */
    private int oneYearBefore;

    /**
     * 今年分数线
     */
    private int currentYear;


    /**
     * 备注
     */
    private String remarks;
}
