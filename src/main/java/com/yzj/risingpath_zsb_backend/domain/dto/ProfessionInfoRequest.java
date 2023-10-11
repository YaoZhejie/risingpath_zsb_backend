package com.yzj.risingpath_zsb_backend.domain.dto;

import com.yzj.risingpath_zsb_backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询专业信息请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProfessionInfoRequest extends PageRequest implements Serializable {
    /**
     * 专业名称
     */
    private String professName;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 专业备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;
}
