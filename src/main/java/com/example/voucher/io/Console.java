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
import com.example.voucher.customer.controller.model.CustomerRequest;
import com.example.voucher.customer.controller.model.CustomerResponse;
import com.example.voucher.customer.service.dto.CustomerDTO;
import com.example.voucher.voucher.controller.model.VoucherRequest;
import com.example.voucher.voucher.controller.model.VoucherResponse;
import com.example.voucher.voucher.service.dto.VoucherDTO;
import com.example.voucher.wallet.controller.model.WalletRequest;
import com.example.voucher.wallet.controller.model.WalletResponse;
import com.example.voucher.wallet.service.dto.WalletDTO;

public class Console {

    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private final Writer writer;
    private final Reader reader;

    public Console() {
        this.writer = new Writer();
        this.reader = new Reader();
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

    public ModeType getWalletModeType() {
        writer.writeMessage(Message.WALLET_MODE_TYPE_SELECTION);

        String input = reader.readString();

        return ModeType.getModeType(input);
    }

    public VoucherRequest getCreateVoucherRequest() {
        VoucherType voucherType = getVoucherType();
        Long discountValue = getDiscountValue();

        return VoucherRequest.builder()
            .setVoucherType(voucherType)
            .setDiscountValue(discountValue)
            .build();
    }

    public VoucherRequest getSearchVoucherRequest() {
        UUID voucherId = getId();

        return VoucherRequest.builder()
            .setVoucherId(voucherId)
            .build();
    }

    public VoucherRequest getUpdateVoucherRequest() {
        UUID voucherId = getId();
        VoucherType voucherType = getVoucherType();
        Long discountValue = getDiscountValue();

        return VoucherRequest.builder()
            .setVoucherId(voucherId)
            .setVoucherType(voucherType)
            .setDiscountValue(discountValue)
            .build();
    }

    public VoucherRequest getDeleteVoucherRequest() {
        UUID voucherId = getId();

        return VoucherRequest.builder()
            .setVoucherId(voucherId)
            .build();
    }

    private VoucherType getVoucherType() {
        writer.writeMessage(Message.VOUCHER_INFO_INPUT_REQUEST);
        writer.writeMessage(Message.VOUCHER_TYPE_SELECTION);

        int number = reader.readInteger();

        return VoucherType.getVouchersType(number);
    }

    private Long getDiscountValue() {
        writer.writeMessage(Message.DISCOUNT_VALUE_INPUT_REQUEST);

        Long discountAmount = reader.readLong();
        validatePositive(discountAmount);

        return discountAmount;
    }

    public CustomerRequest getCreateCustomerRequest() {
        String name = getName();
        String email = getEmail();
        CustomerType customerType = getCustomerType();

        return CustomerRequest.builder()
            .setName(name)
            .setEmail(email)
            .setCustomerType(customerType)
            .build();
    }

    public CustomerRequest getSearchCustomerRequest() {
        UUID customerId = getId();

        return CustomerRequest.builder()
            .setCustomerId(customerId)
            .build();
    }

    public CustomerRequest getUpdateCustomerRequest() {
        UUID customerId = getId();
        String name = getName();
        String email = getEmail();
        CustomerType customerType = getCustomerType();

        return CustomerRequest.builder()
            .setCustomerId(customerId)
            .setName(name)
            .setEmail(email)
            .setCustomerType(customerType)
            .build();
    }

    public CustomerRequest getDeleteCustomerRequest() {
        UUID customerId = getId();

        return CustomerRequest.builder()
            .setCustomerId(customerId)
            .build();
    }

    private String getName() {
        writer.writeMessage(Message.NAME_INPUT_REQUEST);

        String inputName = reader.readString();

        return inputName;
    }

    private String getEmail() {
        writer.writeMessage(Message.NAME_INPUT_EMAIL);

        String inputEmail = reader.readString();

        return inputEmail;
    }

    private CustomerType getCustomerType() {
        writer.writeMessage(Message.CUSTOMER_TYPE_SELECTION);

        String input = reader.readString();

        return CustomerType.getCustomerType(input);
    }

    public WalletRequest getCreateWalletRequest() {
        UUID customerId = getId();
        UUID voucherId = getId();

        return WalletRequest.builder()
            .setCustomerId(customerId)
            .setWalletId(voucherId)
            .build();
    }

    public WalletRequest getSearchByCustomerWalletRequest() {
        UUID customerId = getId();

        return WalletRequest.builder()
            .setCustomerId(customerId)
            .build();
    }

    public WalletRequest getSearchByVoucherWalletRequest() {
        UUID voucherId = getId();

        return WalletRequest.builder()
            .setVoucherId(voucherId)
            .build();
    }

    public WalletRequest getDeleteWalletRequest() {
        UUID customerId = getId();
        UUID voucherId = getId();

        return WalletRequest.builder()
            .setCustomerId(customerId)
            .setVoucherId(voucherId)
            .build();
    }

    public UUID getId() {
        writer.writeMessage(Message.ID_INPUT_REQUEST);

        String input = reader.readString();

        try {
            return UUID.fromString(input);
        } catch (Exception e) {
            return null;
        }

    }

    public void displayVoucherResponse(VoucherResponse voucherResponse) {
        for (VoucherDTO voucher : voucherResponse.getVouchers()) {
            writer.writeVoucherResponse(voucher.voucherId(), voucher.value(), voucher.voucherType());
        }
    }

    public void displayCustomerResponse(CustomerResponse customerResponse) {
        for (CustomerDTO customer : customerResponse.getCustomers()) {
            writer.writeCustomerResponse(customer.customerId(), customer.name(), customer.email(),
                customer.customerType(), customer.createdAt());
        }
    }

    public void displayWalletResponse(WalletResponse walletResponse) {
        for (WalletDTO wallet : walletResponse.getWallets()) {
            writer.writeWalletResponse(wallet.walletId(), wallet.customerId(), wallet.voucherId());
        }
    }

    public void displayError(String errorMsg) {
        logger.error(errorMsg);
        writer.writeMessage(Message.REQUEST_FAILED);
    }

    private void validatePositive(long value) {
        if (value <= 0) {
            throw new IllegalArgumentException(MESSAGE_ERROR_POSITIVE_CONSTRAINT);
        }
    }

}
