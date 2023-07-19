package com.wonu606.vouchermanager.util;

import org.springframework.core.convert.converter.Converter;

public interface TypedConverter<S, T> extends Converter<S, T> {
    Class<S> getSourceType();
    Class<T> getTargetType();

    default boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return getSourceType() == sourceType && getTargetType() == targetType;
    }
}
