package com.wonu606.vouchermanager.service.voucher.converter;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.repository.voucher.query.VoucherInsertQuery;
import com.wonu606.vouchermanager.util.TypedConverter;

public class VoucherInsertQueryConverter implements TypedConverter<Voucher, VoucherInsertQuery> {

    @Override
    public VoucherInsertQuery convert(Voucher voucher) {
        return new VoucherInsertQuery(voucher.getClass().getSimpleName(),
                voucher.getUuid().toString(), voucher.getDiscountValue());
    }

    @Override
    public Class<Voucher> getSourceType() {
        return Voucher.class;
    }

    @Override
    public Class<VoucherInsertQuery> getTargetType() {
        return VoucherInsertQuery.class;
    }
}
