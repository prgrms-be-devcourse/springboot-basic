package com.programmers.part1.order.voucher.entity;

import com.programmers.part1.exception.voucher.VoucherTypeMissingException;

public enum VoucherType {

    FIXED, PERCENT,NONE;

    public static VoucherType getVoucherType(String requestVoucherType){

        if(requestVoucherType.equals("1"))
            return FIXED;
        if(requestVoucherType.equals("2"))
            return PERCENT;
        else
            throw new VoucherTypeMissingException("Voucher Type이 잘못 입력 되었습니다.\n");
    }

}
