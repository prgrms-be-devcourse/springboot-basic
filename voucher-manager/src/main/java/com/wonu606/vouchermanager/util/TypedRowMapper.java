package com.wonu606.vouchermanager.util;

import org.springframework.jdbc.core.RowMapper;

public interface TypedRowMapper<T> extends RowMapper<T> {

    Class<T> getTargetType();

    default boolean canConvert(Class<?> targetType) {
        return getTargetType() == targetType;
    }
}
