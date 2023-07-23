package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.WalletRegisterQuery;
import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherWalletServiceConverterManager {

    private final List<TypedConverter<?, ?>> converterList;

    public VoucherWalletServiceConverterManager() {
        converterList = new ArrayList<>();
        converterList.add(new OwnedVoucherQueryConverter());
        converterList.add(new OwnedVoucherResultConverter());
        converterList.add(new WalletDeleteQueryConverter());
        converterList.add(new WalletInsertQueryConverter());
        converterList.add(new WalletInsertResultConverter());
        converterList.add(new OwnedCustomersQueryConverter());
        converterList.add(new OwnedCustomersResultConverter());
        converterList.add(new WalletRegisterQueryConverter());
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
