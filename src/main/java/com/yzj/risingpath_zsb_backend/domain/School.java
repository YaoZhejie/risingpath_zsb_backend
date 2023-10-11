package com.yzj.risingpath_zsb_backend.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName school
 */
@TableName(value ="school")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class School implements Serializable {
    /**
     * 学校id
     */
    @TableId
    private Integer schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学校地区
     */
    private String city;

    /**
     * 招生办联系电话
     */
    private String schoolPhone;

    /**
     * 学校代码
     */
    private String schoolCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}