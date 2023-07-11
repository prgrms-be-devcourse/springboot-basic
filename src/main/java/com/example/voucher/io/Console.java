package com.example.voucher.io;

import static com.example.voucher.constant.ExceptionMessage.*;
import static com.example.voucher.constant.VoucherServiceType.*;
import static com.example.voucher.io.Writer.*;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.voucher.constant.ExceptionMessage;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.constant.VoucherServiceType;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDTO;

public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private final Writer writer;
    private final Reader reader;

    public Console() {
        this.writer = new Writer();
        this.reader = new Reader();
    }

    public void displayVoucherInfo(VoucherDTO voucher) {
        UUID voucherId = voucher.voucherID();
        Voucher.Type voucherType = voucher.voucherType();
        long discountValue = voucher.discountValue();

        writer.writeMessage(voucherId, voucherType, discountValue);
    }

    public void displayVoucherInfo(List<VoucherDTO> vouchers) {
        for (VoucherDTO voucher : vouchers) {
            UUID voucherId = voucher.voucherID();
            Voucher.Type voucherType = voucher.voucherType();
            long discountValue = voucher.discountValue();

            writer.writeMessage(voucherId, voucherType, discountValue);
        }
    }

    public void displayVoucherCreationError() {
        logger.error(ExceptionMessage.INVALID_ARGUMENT_CANT_CREATE_VOUCHER);
        writer.writeMessage(Message.INVALID_ARGUMENT_CANT_CREATE_VOUCHER);
    }

    public ServiceType getServiceType() {
        writer.writeMessage(Message.SERVICE_TYPE_SELECTION);

        try {
            String input = reader.readString();

            return ServiceType.getServiceType(input);
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT_RETRY_SERVICE_TYPE_SELECTION);

            return null;
        }
    }

    public VoucherRequest getVoucherRequest() {
        try {
            writer.writeMessage(Message.VOUCHER_SERVICE_TYPE_SELECTION);
            String inputVoucherServiceType = reader.readString();
            VoucherServiceType voucherServiceType = VoucherServiceType.getVoucherServiceType(inputVoucherServiceType);
            VoucherRequest voucherRequest = new VoucherRequest(voucherServiceType);

            if (voucherServiceType == CREATE) {
                Voucher.Type voucherType = getVoucherType();
                Long discountValue = getDiscountValue();
                voucherRequest.setVoucherCreateInfo(voucherType, discountValue);
            }

            return voucherRequest;
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION);

            return null;
        }
    }

    private Voucher.Type getVoucherType() {
        writer.writeMessage(Message.VOUCHER_INFO_INPUT_REQUEST);
        writer.writeMessage(Message.VOUCHER_TYPE_SELECTION);
        int number = reader.readInteger();

        return Voucher.Type.getType(number);
    }

    private Long getDiscountValue() {
        writer.writeMessage(Message.DISCOUNT_VALUE_INPUT_REQUEST);

        try {
            Long discountAmount = reader.readLong();
            validatePositive(discountAmount);

            return discountAmount;
        } catch (Exception e) {
            logger.error(e.getMessage());
            writer.writeMessage(Message.INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION);

            return null;
        }
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
