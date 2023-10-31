package com.yzj.risingpath_zsb_backend.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DeleteCollectRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    private Integer schoolId;

    @NotNull
    @NotBlank
    private Integer proId;
}
