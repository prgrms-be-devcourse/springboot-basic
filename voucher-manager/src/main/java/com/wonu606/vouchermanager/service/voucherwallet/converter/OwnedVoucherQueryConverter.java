package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVoucherParam;
import org.springframework.core.convert.converter.Converter;

public class OwnedVoucherQueryConverter implements Converter<OwnedVoucherParam, OwnedVouchersQuery> {

    public OwnedVouchersQuery convert(OwnedVoucherParam param) {
        return new OwnedVouchersQuery(param.getEmail());
    }
}
