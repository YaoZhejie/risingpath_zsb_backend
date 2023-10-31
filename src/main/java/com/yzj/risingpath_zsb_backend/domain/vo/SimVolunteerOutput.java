package com.yzj.risingpath_zsb_backend.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SimVolunteerOutput implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("学校代码")
    private String code;

    @ExcelProperty("学校")
    private String school;

    @ExcelProperty("专业代码")
    private String majorCode;

    @ExcelProperty("专业")
    private String major;
}
