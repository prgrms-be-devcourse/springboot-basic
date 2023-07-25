package com.wonu606.vouchermanager.controller.voucherwallet.converter;

import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import com.wonu606.vouchermanager.util.TypedConverter;

public class VoucherCreateParamConverter implements
        TypedConverter<VoucherCreateRequest, VoucherCreateParam> {

    @Override
    public VoucherCreateParam convert(VoucherCreateRequest request) {
        return new VoucherCreateParam(request.getType(), request.getDiscountValue());
    }

    @Override
    public Class<VoucherCreateRequest> getSourceType() {
        return VoucherCreateRequest.class;
    }

    @Override
    public Class<VoucherCreateParam> getTargetType() {
        return VoucherCreateParam.class;
    }
}
