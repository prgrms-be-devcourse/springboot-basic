package com.wonu606.vouchermanager.service.voucher.converter;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import org.springframework.core.convert.converter.Converter;

public class VoucherCreateQueryConverter implements Converter<Voucher, VoucherInsertQuery> {

    @Override
    public VoucherInsertQuery convert(Voucher voucher) {
        return new VoucherInsertQuery(voucher.getClass().getSimpleName(),
                voucher.getUuid().toString(), voucher.getDiscountValue());
    }
}
