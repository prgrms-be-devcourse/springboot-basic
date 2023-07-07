package com.example.voucher.io;

import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.utils.Validator;

public class Console {

    private final Writer writer;
    private final Reader reader;

    public Console() {
        this.writer = new Writer();
        this.reader = new Reader();

    }

    public void requestModeTypeSelection() {
        writer.writeModeTypeSelection();

    }

    public void requestVoucherInfo() {
        writer.writeVoucherInfoRequest();

    }

    public void requestVoucherTypeSelection() {
        writer.writeVoucherTypeSelection();

    }

    public void requestDiscountValue() {
        writer.writeDiscountValueRequest();

    }

    public void printVoucherInfo(VoucherDTO voucherDTO) {
        VoucherType voucherType = voucherDTO.voucherType();
        String voucherTypeInfo = voucherType.toString();
        long value = voucherDTO.value();

        switch (voucherType) {
            case FIXED_AMOUNT_DISCOUNT -> writer.writeFixedAmountDiscountVoucherInfo(voucherTypeInfo, value);
            case PERCENT_DISCOUNT -> writer.writePercentDiscountVoucherInfo(voucherTypeInfo, value);
        }

    }

    public void printCustomMessage(String customMessage) {
        writer.writeCustomMessage(customMessage);
    }

    public String readModeType() {
        String inputModeType = reader.readString();

        return inputModeType;

    }

    public Integer readVoucherType() {
        Integer inputVoucherType = reader.readInteger();

        return inputVoucherType;

    }

    public long readDiscountValue() {
        Long discountAmount = reader.readLong();
        Validator.validatePositive(discountAmount);

        return discountAmount;

    }

}
