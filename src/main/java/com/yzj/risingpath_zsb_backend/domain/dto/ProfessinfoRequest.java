package com.yzj.risingpath_zsb_backend.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.yzj.risingpath_zsb_backend.domain.School;
import lombok.Data;

/**
 *
 * @TableName professinfo
 */
@TableName(value ="professinfo")
@Data
public class ProfessinfoRequest implements Serializable {
    /**
     * 专业信息表id
     */
    @TableId(type = IdType.AUTO )
    private Integer proId;

    /**
     * 专业类别
     */
    private String type;

    /**
     * 院校id
     */
    private Integer schoolId;

    /**
     * 专业名名称
     */
    private String professName;

    /**
     * 总计划书
     */
    private Integer totalPlan;

    /**
     * 普通计划数
     */
    private Integer troublePlan;

    /**
     *  免试计划数
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

    /**
     * 学校属性
     */
    private School school;

    @TableField(exist = false)
    private static final long serialVersionUID = 12L;
}