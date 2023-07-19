package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import org.springframework.core.convert.converter.Converter;

public class OwnedCustomersQueryConverter implements
        Converter<OwnedCustomersParam, OwnedCustomersQuery> {

    @Override
    public OwnedCustomersQuery convert(OwnedCustomersParam param) {
        return new OwnedCustomersQuery(param.getVoucherUuid().toString());
    }
}
