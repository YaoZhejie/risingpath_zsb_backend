package com.yzj.risingpath_zsb_backend.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yzj.risingpath_zsb_backend.domain.Professinfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class ProfessionAndSchoolVo implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

   private String schoolName;

   private String SchoolCode;


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
     * 总计划数
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



}
