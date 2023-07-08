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
        writer.writeVoucherInfoRequest();
        writer.writeVoucherTypeSelection();

        int number = reader.readInteger();

        return  VoucherType.getVouchersType(number);
    }

    public long readDiscountValue() {
        writer.writeDiscountValueRequest();

        Long discountAmount = reader.readLong();
        validatePositive(discountAmount);

        return discountAmount;
    }

    public ModeType getSelectedType() {
        writer.writeModeTypeSelection();

        try {
            String input = reader.readString();
            ModeType selectedModeType = ModeType.getModeType(input);

            return selectedModeType;
        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleException(new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));

            return null;
        }
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
