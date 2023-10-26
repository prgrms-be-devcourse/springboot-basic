package com.prgrms.vouchermanagement.core.voucher.utils.mapper;

import com.prgrms.vouchermanagement.core.voucher.controller.request.VoucherCreateRequest;
import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;

public class Mapper {

    public static Voucher toVoucher(VoucherCreateRequest voucherCreateRequest) {
        return new Voucher(voucherCreateRequest.getName(), voucherCreateRequest.getAmount(), voucherCreateRequest.getVoucherType());
    }

}
