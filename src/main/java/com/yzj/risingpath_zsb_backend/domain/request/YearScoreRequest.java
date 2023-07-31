package com.yzj.risingpath_zsb_backend.domain.request;

import lombok.Data;

@Data
public class YearScoreRequest {
    private static final long serialVersionUID = 319145526373120793L;

    private  String type;

    private String schoolName;

    private String professName;

    private String city;

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
