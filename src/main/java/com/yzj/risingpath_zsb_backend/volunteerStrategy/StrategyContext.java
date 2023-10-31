package com.yzj.risingpath_zsb_backend.volunteerStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyContext {
    private SelectStrategy gradeStrategy;
}
