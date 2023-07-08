package com.example.voucher.io;

import static com.example.voucher.constant.ExceptionMessage.*;
import static com.example.voucher.io.Writer.*;
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

    public void displayVoucherInfo(List<VoucherDTO> vouchers) {
        for (VoucherDTO voucher : vouchers) {
            VoucherType voucherType = voucher.voucherType();
            long value = voucher.value();

            writer.writeMessage(voucherType, value);
        }
    }

    public void displayCreateResult(boolean isCreated) {
        if (!isCreated) {
            writer.writeMessage(Message.INVALID_ARGUMENT_CANT_CREATE_VOUCHER);
        }
    }

    public VoucherType getVoucherType() {
        writer.writeMessage(Message.VOUCHER_INFO_INPUT_REQUEST);
        writer.writeMessage(Message.VOUCHER_TYPE_SELECTION);

        int number = reader.readInteger();

        return VoucherType.getVouchersType(number);
    }

    public long getDiscountValue() {
        writer.writeMessage(Message.DISCOUNT_VALUE_INPUT_REQUEST);

        Long discountAmount = reader.readLong();
        validatePositive(discountAmount);

        return discountAmount;
    }

    public ModeType getSelectedType() {
        writer.writeMessage(Message.MODE_TYPE_SELECTION);

        try {
            String input = reader.readString();
            ModeType selectedModeType = ModeType.getModeType(input);

            return selectedModeType;
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION);

            return ModeType.NONE;
        }
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
