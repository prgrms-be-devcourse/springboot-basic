package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.service.voucherwallet.param.WalletAssignParam;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;

public class WalletAssignParamConverter implements Converter<WalletAssignRequest, WalletAssignParam> {

    @Override
    public WalletAssignParam convert(WalletAssignRequest request) {
        return new WalletAssignParam(UUID.fromString(request.getVoucherId()));
    }
}
