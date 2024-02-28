package com.yzj.risingpath_zsb_backend.annotation;


import com.yzj.risingpath_zsb_backend.enums.RegexPatternEnum;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImport {

    /**
     * 字段名称
     */
    String value();

    /**
     * 前者为数据库, 后者为展示
     * 导出映射，格式如：0-未知;1-男;2-女
     */
    String kv() default "";

    /**
     * 是否为必填字段（默认为非必填）
     */
    boolean required() default false;

    /**
     * 最大长度（默认255）
     */
    int maxLength() default 255;

    /**
     * 导入唯一性验证（多个字段则取联合验证）
     */
    boolean unique() default false;

    /**
     * 正则匹配,默认为非空字符串
     */
    RegexPatternEnum pattern()  default RegexPatternEnum.STRINGORBLANK;
}