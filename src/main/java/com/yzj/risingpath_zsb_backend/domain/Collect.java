package com.yzj.risingpath_zsb_backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @TableName collect
 */
@TableName(value = "collect")
@Data
public class Collect implements Serializable {
    /**
     * 收藏志愿的id
     */
    @TableId(type = IdType.AUTO)
    private Integer collectId;

    /**
     * 学校的id
     */
    private Integer schoolId;

    /**
     * 专业的id
     */
    private Integer proId;

    /**
     * 用户的id
     */
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}