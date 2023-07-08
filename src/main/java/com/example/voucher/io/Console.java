package com.example.voucher.io;

import static com.example.voucher.constant.ExceptionMessage.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.voucher.constant.ModeType;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.constant.VoucherType;

public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private final Writer writer;
    private final Reader reader;

    public Console() {
        this.writer = new Writer();
        this.reader = new Reader();
    }

    public void printVoucherInfo(List<VoucherDTO> vouchers) {
        for (VoucherDTO voucher : vouchers) {
            VoucherType voucherType = voucher.voucherType();
            String voucherTypeInfo = voucherType.toString();
            long value = voucher.value();

            switch (voucherType) {
                case FIXED_AMOUNT_DISCOUNT -> writer.writeFixedAmountDiscountVoucherInfo(voucherTypeInfo, value);
                case PERCENT_DISCOUNT -> writer.writePercentDiscountVoucherInfo(voucherTypeInfo, value);
            }
        }
    }

    public VoucherType readVoucherType() {
        writer.writeVoucherInfoRequest();
        writer.writeVoucherTypeSelection();

        int number = reader.readInteger();

        return VoucherType.getVouchersType(number);
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
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeCustomMessage(e.getMessage());

            return null;
        }
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
