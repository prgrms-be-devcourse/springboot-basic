package com.example.demo.voucher.presentation.message;

import com.example.demo.voucher.application.VoucherType;

public class VoucherTypeMessageInfo {

    private final VoucherTypeMessageMapper voucherTypeMessageMapper;

    public VoucherTypeMessageInfo(VoucherTypeMessageMapper voucherTypeMessageMapper) {
        this.voucherTypeMessageMapper = voucherTypeMessageMapper;
    }

    public String toMessage(VoucherType type) {
        return getMessage(type).getMessage();
    }
    private VoucherTypeMessage getMessage(VoucherType type) {
        return voucherTypeMessageMapper.getMessage(type);
    }


}
