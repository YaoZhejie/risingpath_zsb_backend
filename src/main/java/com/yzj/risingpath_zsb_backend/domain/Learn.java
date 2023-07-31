package com.yzj.risingpath_zsb_backend.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName learn
 */
@TableName(value ="learn")
@Data
public class Learn implements Serializable {
    /**
     * 下载资料id
     */
    @TableId(type = IdType.AUTO)
    private Integer learnId;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 下载链接
     */
    private String downloadLink;

    /**
     * 
     */
    private String code;

    /**
     * 发布时间
     */
    @TableField(fill= FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}