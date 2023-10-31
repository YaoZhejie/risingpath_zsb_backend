package com.yzj.risingpath_zsb_backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName yearsocre
 */
@TableName(value = "yearsocre")
@Data
public class Yearsocre implements Serializable {
    /**
     * 分数表id
     */
    @TableId(type = IdType.AUTO)
    private Integer scoreId;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 专业信息id
     */
    private Integer proId;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 录取最高分
     */
    private Integer maxScore;

    /**
     * 录取最低分
     */
    private Integer minScore;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}