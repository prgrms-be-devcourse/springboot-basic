package com.example.demo.voucher.presentation.message;

import com.example.demo.voucher.application.VoucherType;

import java.util.Map;
import java.util.HashMap;

public class VoucherTypeMessageMapper {
    private static final Map<VoucherType, VoucherTypeMessage> mapping = new HashMap<>();
    private static final VoucherTypeMessageMapper INSTANCE = new VoucherTypeMessageMapper();

    private VoucherTypeMessageMapper() {
        mapping.put(VoucherType.FIXED_AMOUNT_VOUCHER, VoucherTypeMessage.FIXED_AMOUNT_VOUCHER);
        mapping.put(VoucherType.PERCENT_DISCOUNT_VOUCHER, VoucherTypeMessage.PERCENT_DISCOUNT_VOUCHER);
    }

    public static VoucherTypeMessageMapper getInstance() {
        return INSTANCE;
    }

    public VoucherTypeMessage getMessage(VoucherType type) {
        return mapping.get(type);
    }
}