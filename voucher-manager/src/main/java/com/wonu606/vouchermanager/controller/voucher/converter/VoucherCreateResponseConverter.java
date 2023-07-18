package com.wonu606.vouchermanager.controller.voucher.converter;

import com.wonu606.vouchermanager.controller.voucher.response.VoucherCreateResponse;
import com.wonu606.vouchermanager.service.voucher.result.VoucherCreateResult;
import org.springframework.core.convert.converter.Converter;

public class VoucherCreateResponseConverter implements Converter<VoucherCreateResult, VoucherCreateResponse> {

    @Override
    public VoucherCreateResponse convert(VoucherCreateResult result) {
        return new VoucherCreateResponse(result.getTaskSuccess());
    }
}
