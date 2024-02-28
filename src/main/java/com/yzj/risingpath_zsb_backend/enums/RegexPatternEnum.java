package com.yzj.risingpath_zsb_backend.enums;

import lombok.Getter;

@Getter
public enum RegexPatternEnum {
    STRING("^.+$", "该只能为非空字符串！"),
    STRINGORBLANK("^.*$", "该只能为字符串或空！"),
    INTEGERORBLANK("^[0-9]*$","该数据只能为数字或空！"),
    INTEGER("^[0-9]+$","该数据只能为非空数字！"),
    DATE("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$","日期格式必须为yyyy-MM-dd HH:mm:ss！"),
    DECIMAL("^[0-9]+(\\.[0-9]{1,})?$", "该数据只能为小数！"),
    PHONE("^1[3-9]\\d{9}$","填写的手机号不正确！");

    private final String regex;
    private final String message;

    RegexPatternEnum(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }
}
