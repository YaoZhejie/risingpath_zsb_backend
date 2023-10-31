package com.yzj.risingpath_zsb_backend.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出模拟志愿输出
 */
@Data
public class CollectOutput implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @ExcelProperty("学校")
    private String schoolName;

    @ExcelProperty("学校代码")
    private String schoolCode;


    /**
     * 专业类别
     */
    @ExcelProperty("专业类别")
    private String type;

    /**
     * 专业代码
     */
    @ExcelProperty("专业代码")
    private String proCode;

    /**
     * 专业名名称
     */
    @ExcelProperty("专业名名称")
    private String professName;

    /**
     * 总计划数
     */
    @ExcelProperty("总计划数")
    private Integer totalPlan;

    /**
     * 普通计划数
     */
    @ExcelProperty("普通计划数")
    private Integer troublePlan;

    /**
     *  免试计划数
     */
    @ExcelProperty("免试计划数")
    private Integer soldierPlan;

    /**
     * 学费
     */
    @ExcelProperty("学费")
    private Integer tuition;

    /**
     * 英语要求
     */
    @ExcelProperty("英语要求")
    private String englishReq;

    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remarks;
}
