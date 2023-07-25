package com.wonu606.vouchermanager.service.voucher.converter;

import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class VoucherResultConverter implements TypedConverter<VoucherResultSet, VoucherResult> {

    @Override
    public VoucherResult convert(VoucherResultSet resultSet) {
        return new VoucherResult(resultSet.getUuid(), resultSet.getVoucherClassType(),
                resultSet.getDiscountValue());
    }

    @Override
    public Class<VoucherResultSet> getSourceType() {
        return VoucherResultSet.class;
    }

    @Override
    public Class<VoucherResult> getTargetType() {
        return VoucherResult.class;
    }
}
