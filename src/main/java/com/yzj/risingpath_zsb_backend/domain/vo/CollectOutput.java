package com.yzj.risingpath_zsb_backend.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yzj.risingpath_zsb_backend.annotation.ExcelExport;
import com.yzj.risingpath_zsb_backend.annotation.ExcelImport;
import lombok.Data;

import java.io.Serializable;

/**
 * 导出模拟志愿输出
 */
@Data
public class CollectOutput implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @ExcelExport(value = "学校名称")
    @ExcelImport(value = "学校名称")
    private String schoolName;

    @ExcelExport(value = "学校代码")
    @ExcelImport(value = "学校代码")
    private String schoolCode;


    /**
     * 专业类别
     */
    @ExcelExport(value = "专业类别")
    @ExcelImport(value = "专业类别")
    private String type;

    /**
     * 专业代码
     */
    @ExcelExport(value = "专业代码")
    @ExcelImport(value = "专业代码")
    private String proCode;

    /**
     * 专业名名称
     */
    @ExcelExport(value = "专业名称")
    @ExcelImport(value = "专业名称")
    private String professName;

    /**
     * 总计划数
     */
    @ExcelExport(value = "总计划数")
    @ExcelImport(value = "总计划数")
    private Integer totalPlan;

    /**
     * 普通计划数
     */
    @ExcelExport(value = "普通计划数")
    @ExcelImport(value = "普通计划数")
    private Integer troublePlan;

    /**
     *  免试计划数
     */
    @ExcelExport(value = "免试计划数")
    @ExcelImport(value = "免试计划数")
    private Integer soldierPlan;

    /**
     * 学费
     */
    @ExcelExport(value = "学费")
    @ExcelImport(value = "学费")
    private Integer tuition;

    /**
     * 英语要求
     */
    @ExcelExport(value = "英语要求")
    @ExcelImport(value = "英语要求")
    private String englishReq;

    /**
     * 备注
     */
    @ExcelExport(value = "备注")
    @ExcelImport(value = "备注")
    private String remarks;
}
