package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.service.voucher.param.VoucherCreateParam;
import org.springframework.core.convert.converter.Converter;

public class VoucherCreateParamConverter implements Converter<VoucherCreateRequest, VoucherCreateParam> {

    @Override
    public VoucherCreateParam convert(VoucherCreateRequest request) {
        return new VoucherCreateParam(request.getType(), request.getDiscountValue());
    }
}
