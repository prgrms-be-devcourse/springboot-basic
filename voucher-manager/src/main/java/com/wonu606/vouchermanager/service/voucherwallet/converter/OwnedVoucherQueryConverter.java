package com.wonu606.vouchermanager.service.voucherwallet.converter;

import com.wonu606.vouchermanager.service.voucherwallet.param.OwnedVouchersParam;
import com.wonu606.vouchermanager.repository.voucherwallet.query.OwnedVouchersQuery;
import com.wonu606.vouchermanager.util.TypedConverter;

public class OwnedVoucherQueryConverter implements
        TypedConverter<OwnedVouchersParam, OwnedVouchersQuery> {

    public OwnedVouchersQuery convert(OwnedVouchersParam param) {
        return new OwnedVouchersQuery(param.getEmail());
    }

    @Override
    public Class<OwnedVouchersParam> getSourceType() {
        return OwnedVouchersParam.class;
    }

    @Override
    public Class<OwnedVouchersQuery> getTargetType() {
        return OwnedVouchersQuery.class;
    }
}
