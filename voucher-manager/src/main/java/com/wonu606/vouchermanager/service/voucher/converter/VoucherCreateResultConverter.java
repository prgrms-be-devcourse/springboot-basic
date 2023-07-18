package com.wonu606.vouchermanager.service.voucher.converter;

import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherInsertResultSet;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import org.springframework.core.convert.converter.Converter;

public class VoucherCreateResultConverter implements
        Converter<VoucherInsertResultSet, VoucherCreateResult> {

    @Override
    public VoucherCreateResult convert(VoucherInsertResultSet param) {
        return new VoucherCreateResult(param.getAffectedRowsCount() == 1);
    }
}
