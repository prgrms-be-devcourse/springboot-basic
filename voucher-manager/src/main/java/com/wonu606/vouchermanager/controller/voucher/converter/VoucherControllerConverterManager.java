package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.ArrayList;
import java.util.List;

public class VoucherControllerConverterManager {

    private final List<TypedConverter<?, ?>> converterList;

    public VoucherControllerConverterManager() {
        converterList = new ArrayList<>();
        converterList.add(new VoucherCreateParamConverter());
        converterList.add(new VoucherCreateResponseConverter());
        converterList.add(new VoucherResponseConverter());
        converterList.add(new OwnedCustomersParamConverter());
        converterList.add(new OwnedCustomerResponseConverter());
        converterList.add(new WalletAssignParamConverter());
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
