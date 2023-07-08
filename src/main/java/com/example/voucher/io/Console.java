package com.example.voucher.io;

import static com.example.voucher.utils.ExceptionMessage.*;

import java.util.List;

import com.example.voucher.constant.ModeType;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.utils.ExceptionHandler;
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

    public void printVoucherInfo(List<VoucherDTO> vouchers){
        for( VoucherDTO voucher : vouchers){
            VoucherType voucherType = voucher.voucherType();
            String voucherTypeInfo = voucherType.toString();
            long value = voucher.value();

            switch (voucherType) {
                case FIXED_AMOUNT_DISCOUNT -> writer.writeFixedAmountDiscountVoucherInfo(voucherTypeInfo, value);
                case PERCENT_DISCOUNT -> writer.writePercentDiscountVoucherInfo(voucherTypeInfo, value);
            }
        }
    }

    public void printCustomMessage(String customMessage) {
        writer.writeCustomMessage(customMessage);
    }

    public VoucherType readVoucherType() {
        this.requestVoucherInfo();
        this.requestVoucherTypeSelection();

        int number = reader.readInteger();

        return Validator.validateVoucherTypeMatch(number);
    }

    public long readDiscountValue() {
        this.requestDiscountValue();

        Long discountAmount = reader.readLong();
        Validator.validatePositive(discountAmount);

        return discountAmount;
    }

    public ModeType getSelectedType() {
        this.requestModeTypeSelection();

        try {
            String input = reader.readString();
            ModeType selectedModeType = Validator.validateModeTypeMatch(input);

            return selectedModeType;
        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleException(new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));

            return null;
        }
    }


}
