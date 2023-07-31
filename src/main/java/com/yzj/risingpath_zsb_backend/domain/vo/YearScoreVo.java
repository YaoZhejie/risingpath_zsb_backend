package com.yzj.risingpath_zsb_backend.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class YearScoreVo implements Serializable {

    /**
     * 分数表id
     */
    @TableId(type = IdType.AUTO )
    private Integer scoreId;

    /**
     * 学校id
     */
    private Integer schoolId;

    private String schoolName;


    /**
     * 专业信息id
     */
    private Integer proId;

    private String professName;
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
