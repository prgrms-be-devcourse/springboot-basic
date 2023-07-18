package com.wonu606.vouchermanager.service.voucher.converter;

import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherResultSet;
import com.wonu606.vouchermanager.service.voucher.result.VoucherResult;
import org.springframework.core.convert.converter.Converter;

public class VoucherResultConverter implements Converter<VoucherResultSet, VoucherResult> {

    @Override
    public VoucherResult convert(VoucherResultSet resultSet) {
        return new VoucherResult(resultSet.getSimpleName(), resultSet.getUuid(),
                resultSet.getDiscountValue());
    }
}
