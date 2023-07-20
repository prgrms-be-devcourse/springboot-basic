package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedCustomersParam;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedCustomersQuery;
import com.wonu606.vouchermanager.util.TypedConverter;

public class OwnedCustomersQueryConverter implements
        TypedConverter<OwnedCustomersParam, OwnedCustomersQuery> {

    @Override
    public OwnedCustomersQuery convert(OwnedCustomersParam param) {
        return new OwnedCustomersQuery(param.getVoucherUuid().toString());
    }

    @Override
    public Class<OwnedCustomersParam> getSourceType() {
        return OwnedCustomersParam.class;
    }

    @Override
    public Class<OwnedCustomersQuery> getTargetType() {
        return OwnedCustomersQuery.class;
    }
}
