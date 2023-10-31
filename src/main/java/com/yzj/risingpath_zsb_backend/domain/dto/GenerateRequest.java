package com.yzj.risingpath_zsb_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRequest implements Serializable {
    private static final long serialVersionUID = 314561716373120793L;


    private String type;


    private Integer score;


    private String remarks;


    private Integer hours;


    private Integer regularity;


    private Integer method;


    private Integer background;


    private Integer start;


}
