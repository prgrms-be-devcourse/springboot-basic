package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import com.wonu606.vouchermanager.util.TypedConverter;
import java.util.UUID;

public class WalletAssignParamConverter implements
        TypedConverter<WalletAssignRequest, WalletAssignParam> {

    @Override
    public WalletAssignParam convert(WalletAssignRequest request) {
        return new WalletAssignParam(UUID.fromString(request.getVoucherId()));
    }

    @Override
    public Class<WalletAssignRequest> getSourceType() {
        return WalletAssignRequest.class;
    }

    @Override
    public Class<WalletAssignParam> getTargetType() {
        return WalletAssignParam.class;
    }
}
