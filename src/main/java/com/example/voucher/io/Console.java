package com.example.voucher.io;

import static com.example.voucher.constant.ExceptionMessage.*;
import static com.example.voucher.io.Writer.*;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.domain.dto.VoucherDTO;

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

    public void displayVoucherServiceError(String errorMsg) {
        logger.error(errorMsg);
        writer.writeMessage(Message.REQUEST_FAILED);
    }

    public ServiceType getServiceType() {
        writer.writeMessage(Message.SERVICE_TYPE_SELECTION);

        try {
            String input = reader.readString();
            ServiceType selectedServiceType = ServiceType.getServiceType(input);

            return selectedServiceType;
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT);

            return null;
        }
    }

    public ModeType getModeType() {
        writer.writeMessage(Message.MODE_TYPE_SELECTION);

        try {
            String input = reader.readString();
            ModeType selectedModeType = ModeType.getModeType(input);

            return selectedModeType;
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT);

            return null;
        }
    }

    public VoucherRequest.Create getCreateRequest() {
        VoucherType voucherType = getVoucherType();

        if (voucherType == null) {
            return null;
        }
        Long discountValue = getDiscountValue();

        if (discountValue == null) {
            return null;
        }

        return new VoucherRequest.Create(voucherType, discountValue);
    }

    public VoucherRequest.Update getUpdateRequest() {
        VoucherType voucherType = getVoucherType();

        if (voucherType == null) {
            return null;
        }
        Long discountValue = getDiscountValue();

        if (discountValue == null) {
            return null;
        }

        UUID voucherID = getVoucherId();

        if (discountValue == null) {
            return null;
        }

        return new VoucherRequest.Update(voucherID, voucherType, discountValue);
    }

    public VoucherType getVoucherType() {
        writer.writeMessage(Message.VOUCHER_INFO_INPUT_REQUEST);
        writer.writeMessage(Message.VOUCHER_TYPE_SELECTION);

        try {
            int number = reader.readInteger();

            return VoucherType.getVouchersType(number);
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT);

            return null;
        }
    }

    public Long getDiscountValue() {
        writer.writeMessage(Message.DISCOUNT_VALUE_INPUT_REQUEST);

        try {
            Long discountAmount = reader.readLong();
            validatePositive(discountAmount);

            return discountAmount;
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT);

            return null;
        }
    }

    public UUID getVoucherId() {
        writer.writeMessage(Message.ID_INPUT_REQUEST);

        try {
            String input = reader.readString();

            return UUID.fromString(input);
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT);

            return null;
        }
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
