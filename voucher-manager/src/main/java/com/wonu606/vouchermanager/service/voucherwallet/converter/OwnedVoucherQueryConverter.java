package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import org.springframework.core.convert.converter.Converter;

public class OwnedVoucherQueryConverter implements
        Converter<OwnedVouchersParam, OwnedVouchersQuery> {

    public OwnedVouchersQuery convert(OwnedVouchersParam param) {
        return new OwnedVouchersQuery(param.getEmail());
    }
}
