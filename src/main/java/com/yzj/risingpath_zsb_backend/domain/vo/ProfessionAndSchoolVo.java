package com.yzj.risingpath_zsb_backend.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class ProfessionAndSchoolVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("")
    private String schoolName;

    private String schoolCode;


    /**
     * 专业类别
     */
    private String type;

    /**
     * 院校id
     */
    private Integer schoolId;

    /**
     * 专业id
     */
    private Integer proId;
    /**
     * 专业代码
     */
    private String proCode;
    /**
     * 专业名名称
     */
    private String professName;

    /**
     * 总计划数
     */
    private Integer totalPlan;

    /**
     * 普通计划数
     */
    private Integer troublePlan;

    /**
     * 免试计划数
     */
    private Integer soldierPlan;

    /**
     * 学费
     */
    private Integer tuition;

    /**
     * 英语要求
     */
    private String englishReq;

    /**
     * 备注
     */
    private String remarks;

    private Integer total;

}
