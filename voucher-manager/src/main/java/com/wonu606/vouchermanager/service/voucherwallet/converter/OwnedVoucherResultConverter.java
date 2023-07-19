package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.resultset.OwnedVoucherResultSet;
import com.wonu606.vouchermanager.service.voucherwallet.result.OwnedVoucherResult;
import org.springframework.core.convert.converter.Converter;

public class OwnedVoucherResultConverter implements
        Converter<OwnedVoucherResultSet, OwnedVoucherResult> {

    public OwnedVoucherResult convert(OwnedVoucherResultSet resultSet) {
        return new OwnedVoucherResult(resultSet.getVoucherId());
    }
}
