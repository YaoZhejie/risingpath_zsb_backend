package com.yzj.risingpath_zsb_backend.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ProfessionTypeVo implements Serializable {
    /**
     * 专业类型
     */
    private String type;

    /**
     * 专业数量
     */
    private Integer professinfoNumber;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
