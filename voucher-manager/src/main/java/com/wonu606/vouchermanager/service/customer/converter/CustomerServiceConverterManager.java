package com.wonu606.vouchermanager.service.customer.converter;

import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceConverterManager {

    private final List<TypedConverter<?, ?>> converterList;

    public CustomerServiceConverterManager() {
        converterList = new ArrayList<>();
        converterList.add(new CustomerCreateQueryConverter());
        converterList.add(new CustomerCreateResultConverter());
        converterList.add(new CustomerResultConverter());
    }

    @SuppressWarnings("unchecked")
    public <S, T> T convert(S source, Class<T> targetType) {
        for (TypedConverter<?, ?> converter : converterList) {
            if (converter.canConvert(source.getClass(), targetType)) {
                return ((TypedConverter<S, T>) converter).convert(source);
            }
        }
        throw new IllegalArgumentException(
                source.getClass() + "타입을 " + targetType + "로 변환할 수 없습니다.");
    }
}
