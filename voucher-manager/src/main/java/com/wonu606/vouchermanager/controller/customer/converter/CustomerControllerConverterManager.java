package com.wonu606.vouchermanager.controller.customer.converter;

import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.ArrayList;
import java.util.List;

public class CustomerControllerConverterManager {

    private final List<TypedConverter<?, ?>> converterList;

    public CustomerControllerConverterManager() {
        converterList = new ArrayList<>();
        converterList.add(new CustomerCreateParamConverter());
        converterList.add(new CustomerCreateResponseConverter());
        converterList.add(new CustomerResponseConverter());
        converterList.add(new OwnedVouchersParamConverter());
        converterList.add(new OwnedVoucherResponseConverter());
        converterList.add(new WalletDeleteParamConverter());
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
