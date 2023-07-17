package com.example.voucher.io;

import static com.example.voucher.constant.ExceptionMessage.*;
import static com.example.voucher.io.Writer.*;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.controller.request.VoucherRequest;

public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private final Writer writer;
    private final Reader reader;

    public Console() {
        this.writer = new Writer();
        this.reader = new Reader();
    }

    public void displayResponse(String resultInfo) {
        writer.writeMessage(resultInfo);
    }

    public void displayError(String errorMsg) {
        logger.error(errorMsg);
        writer.writeMessage(Message.REQUEST_FAILED);
    }

    public ServiceType getServiceType() {
        writer.writeMessage(Message.SERVICE_TYPE_SELECTION);

        String input = reader.readString();

        return ServiceType.getServiceType(input);
    }

    public ModeType getModeType() {
        writer.writeMessage(Message.MODE_TYPE_SELECTION);

        String input = reader.readString();

        return ModeType.getModeType(input);
    }

    public VoucherRequest.Create getCreateRequest() {
        VoucherType voucherType = getVoucherType();
        Long discountValue = getDiscountValue();

        return new VoucherRequest.Create(voucherType, discountValue);
    }

    public VoucherRequest.Update getUpdateRequest() {
        VoucherType voucherType = getVoucherType();
        Long discountValue = getDiscountValue();
        UUID voucherID = getVoucherId();

        return new VoucherRequest.Update(voucherID, voucherType, discountValue);
    }

    public VoucherType getVoucherType() {
        writer.writeMessage(Message.VOUCHER_INFO_INPUT_REQUEST);
        writer.writeMessage(Message.VOUCHER_TYPE_SELECTION);

        int number = reader.readInteger();

        return VoucherType.getVouchersType(number);

    }

    public Long getDiscountValue() {
        writer.writeMessage(Message.DISCOUNT_VALUE_INPUT_REQUEST);

        Long discountAmount = reader.readLong();
        validatePositive(discountAmount);

        return discountAmount;
    }

    public UUID getVoucherId() {
        writer.writeMessage(Message.ID_INPUT_REQUEST);

        String input = reader.readString();

        return UUID.fromString(input);
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
