package com.yzj.risingpath_zsb_backend.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;

@Data
public class SimulationMajorVo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 319145526373120793L;

    private String professName;

    private String proCode;
}
