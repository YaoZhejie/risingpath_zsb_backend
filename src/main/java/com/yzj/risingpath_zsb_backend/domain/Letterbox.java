package com.yzj.risingpath_zsb_backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * @TableName letterbox
 */
@TableName(value ="letterbox")
@Data
public class Letterbox implements Serializable {
    /**
     * 信唯一标识
     */
    @TableId(type = IdType.AUTO )
    private Integer letterId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}