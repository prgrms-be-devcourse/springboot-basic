package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.io.CommandType;
import com.programmers.voucher.io.Input;
import com.programmers.voucher.io.Message;
import com.programmers.voucher.io.Output;
import com.programmers.voucher.model.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherProgram {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public VoucherProgram(Input input, Output output, VoucherController voucherController, CustomerController customerController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            try {
                switch (getInputCommandType()) {
                    case EXIT -> isRunning = false;
                    case CREATE_VOUCHER -> voucherController.create(getInputVoucherType(), getInputDiscountValue());
                    case LIST_VOUCHER -> voucherController.findAll();
                    case SELECT_VOUCHER -> voucherController.findById(getInputVoucherId());
                    case UPDATE_VOUCHER -> voucherController.update(getInputVoucherId(), getInputDiscountValue(), getInputVoucherType());
                    case DELETE_ALL_VOUCHER -> voucherController.deleteAll();
                    case CREATE_CUSTOMER -> customerController.createCustomer(getCustomerDto());
                    case SELECT_CUSTOMER_BY_VOUCHER -> customerController.findCustomerByVoucher(getInputVoucherId());
                    case ASSIGN_VOUCHER -> voucherController.assign(getInputVoucherId(), getInputEmail());
                    case LIST_VOUCHERS_OF_CUSTOMER -> voucherController.findAllByCustomer(getInputEmail());
                    case DELETE_VOUCHERS_OF_CUSTOMER -> voucherController.deleteByCustomer(getInputEmail());
                }
            } catch (IllegalArgumentException e) {
                logger.error("wrong order input");
                output.printError(e.getMessage());
            }
        }
    }

    private CommandType getInputCommandType() {
        output.print(Message.INTRO_MESSAGE);
        String command = input.getInput();
        return CommandType.toCommandType(command);
    }

    private VoucherType getInputVoucherType() {
        output.print(Message.REQUEST_VOUCHER_TYPE_MESSAGE);
        return VoucherType.toVoucherType(input.getInput());
    }

    private long getInputDiscountValue() {
        output.print(Message.REQUEST_DISCOUNT_VALUE_MESSAGE);
        return Long.parseLong(input.getInput());
    }

    private UUID getInputVoucherId() {
        output.print(Message.REQUEST_VOUCHER_ID);
        return UUID.fromString(input.getInput());
    }

    private CustomerDto getCustomerDto() {
        return new CustomerDto(getInputCustomerName(), getInputEmail());
    }

    private String getInputCustomerName() {
        output.print(Message.REQUEST_CUSTOMER_NAME);
        return input.getInput();
    }

    private String getInputEmail() {
        output.print(Message.REQUEST_CUSTOMER_EMAIL);
        return input.getInput();
    }
}
