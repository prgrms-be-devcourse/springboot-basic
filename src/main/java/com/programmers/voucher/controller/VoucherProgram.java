package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.io.CommandType;
import com.programmers.voucher.io.View;
import com.programmers.voucher.model.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherProgram implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final View view;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public VoucherProgram(View view, VoucherController voucherController, CustomerController customerController) {
        this.view = view;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    @Override
    public void run(ApplicationArguments args) {
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
                view.printError(e.getMessage());
            }
        }
    }

    private CommandType getInputCommandType() {
        view.requestMenuType();
        String command = view.getInput();
        return CommandType.toCommandType(command);
    }

    private VoucherType getInputVoucherType() {
        view.requestVoucherType();
        return VoucherType.toVoucherType(view.getInput());
    }

    private long getInputDiscountValue() {
        view.requestDiscountValue();
        return view.getInputDiscountValue();
    }

    private UUID getInputVoucherId() {
        view.requestVoucherId();
        return UUID.fromString(view.getInput());
    }

    private CustomerDto getCustomerDto() {
        return new CustomerDto(getInputCustomerName(), getInputEmail());
    }

    private String getInputCustomerName() {
        view.requestCustomerName();
        return view.getInput();
    }

    private String getInputEmail() {
        view.requestEmail();
        return view.getInput();
    }
}
