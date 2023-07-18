package com.example.voucher.io;

import static com.example.voucher.constant.ExceptionMessage.*;
import static com.example.voucher.io.Writer.*;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.voucher.constant.CustomerType;
import com.example.voucher.constant.ModeType;
import com.example.voucher.constant.ServiceType;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.controller.request.CustomerRequest;
import com.example.voucher.controller.request.VoucherRequest;
import com.example.voucher.controller.request.WalletRequest;

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

    public WalletRequest.Create getWalletCreateRequest() {
        UUID customerId = getId();
        UUID voucherId = getId();

        return new WalletRequest.Create(customerId, voucherId);
    }

    public WalletRequest.Update getWalletUpdateRequest() {
        UUID walletId = getId();
        UUID customerId = getId();
        UUID voucherId = getId();

        return new WalletRequest.Update(walletId, customerId, voucherId);
    }

    public VoucherRequest.Create getVoucherCreateRequest() {
        VoucherType voucherType = getVoucherType();
        Long discountValue = getDiscountValue();

        return new VoucherRequest.Create(voucherType, discountValue);
    }

    public VoucherRequest.Update getVoucherUpdateRequest() {
        VoucherType voucherType = getVoucherType();
        Long discountValue = getDiscountValue();
        UUID voucherId = getId();

        return new VoucherRequest.Update(voucherId, voucherType, discountValue);
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

    public CustomerRequest.Create getCustomerCreateRequest() {
        String name = getName();
        String email = getEmail();
        CustomerType customerType = getCustomerType();

        return new CustomerRequest.Create(name, email, customerType);
    }

    public CustomerRequest.Update getCustomerUpdateRequest() {
        UUID customerId = getId();
        String name = getName();
        String email = getEmail();
        CustomerType customerType = getCustomerType();

        return new CustomerRequest.Update(customerId, name, email, customerType);
    }

    public UUID getId() {
        writer.writeMessage(Message.ID_INPUT_REQUEST);

        String input = reader.readString();

        return UUID.fromString(input);
    }

    public String getName() {
        writer.writeMessage(Message.NAME_INPUT_REQUEST);

        String inputName = reader.readString();

        return inputName;
    }

    public String getEmail() {
        writer.writeMessage(Message.NAME_INPUT_EMAIL);

        String inputEmail = reader.readString();

        return inputEmail;
    }

    public CustomerType getCustomerType() {
        writer.writeMessage(Message.CUSTOMER_TYPE_SELECTION);

        String input = reader.readString();

        return CustomerType.getCustomerType(input);
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
