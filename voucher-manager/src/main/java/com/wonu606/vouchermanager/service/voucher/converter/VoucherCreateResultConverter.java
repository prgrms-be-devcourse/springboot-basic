package com.wonu606.vouchermanager.service.voucher.converter;

import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import com.wonu606.vouchermanager.util.TypedConverter;

public class VoucherCreateResultConverter implements
        TypedConverter<VoucherInsertResultSet, VoucherCreateResult> {

    @Override
    public VoucherCreateResult convert(VoucherInsertResultSet param) {
        return new VoucherCreateResult(param.getAffectedRowsCount() == 1);
    }

    @Override
    public Class<VoucherInsertResultSet> getSourceType() {
        return VoucherInsertResultSet.class;
    }

    @Override
    public Class<VoucherCreateResult> getTargetType() {
        return VoucherCreateResult.class;
    }
}
