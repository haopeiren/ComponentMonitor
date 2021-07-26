package com.ffcs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将类属性和表字段做对应
 * 解析数据时，解析{@link #columnName()}的值到对应属性上
 *
 * @author haopeiren
 * @since 2021/7/16
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableField {
    /**
     * 数据库字段名，为空时使用属性名
     */
    String columnName() default "";

    /**
     * 字段类型
     */
    FieldType fieldType();
}
