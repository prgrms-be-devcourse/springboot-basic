package com.programmers.voucher.controller;

import com.programmers.voucher.controller.dto.CustomerDto;
import com.programmers.voucher.io.CommandType;
import com.programmers.voucher.io.View;
import com.programmers.voucher.model.customer.Customer;
import com.programmers.voucher.model.voucher.Voucher;
import com.programmers.voucher.model.voucher.VoucherType;
import com.programmers.voucher.service.CustomerService;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherProgram implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final View view;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherProgram(View view, VoucherService voucherService, CustomerService customerService) {
        this.view = view;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(ApplicationArguments args) {
        boolean isRunning = true;

        while (isRunning) {
            try {
                switch (getCommandType()) {
                    case EXIT -> isRunning = false;
                    case CREATE_VOUCHER -> createVoucher();
                    case LIST_VOUCHER -> findAllVouchers();
                    case SELECT_VOUCHER -> findVoucher();
                    case UPDATE_VOUCHER -> updateVoucher();
                    case DELETE_ALL_VOUCHER -> deleteAllVoucher();
                    case CREATE_CUSTOMER -> createCustomer();
                }
            } catch (IllegalArgumentException e) {
                logger.error("wrong order input");
                view.printError(e.getMessage());
            }
        }
    }

    private void createVoucher() {
        voucherService.create(getVoucherType(), getDiscountValue());
    }

    private void findAllVouchers() {
        checkEmptyVoucher(voucherService.findAllVoucher());
    }

    private void findVoucher() {
        Voucher selected = voucherService.findById(getVoucherId());
        view.printVoucher(selected);
    }

    private void updateVoucher() {
        Voucher updated = voucherService.update(getVoucherId(), getDiscountValue(), getVoucherType());
        view.printVoucher(updated);
    }

    private void deleteAllVoucher() {
        voucherService.deleteAll();
        view.printDeleteAll();
    }

    private void createCustomer() {
        Customer customer = customerService.create(getCustomerDto());
        view.printCustomer(customer);
    }

    private CommandType getCommandType() {
        view.requestMenuType();
        String command = view.getInput();
        return CommandType.toCommandType(command);
    }

    private VoucherType getVoucherType() {
        view.requestVoucherType();
        return VoucherType.toVoucherType(view.getInput());
    }

    private long getDiscountValue() {
        view.requestDiscountValue();
        return view.getInputDiscountValue();
    }

    private UUID getVoucherId() {
        view.requestVoucherId();
        return UUID.fromString(view.getInput());
    }

    private void checkEmptyVoucher(List<Voucher> vouchers) {
        if (vouchers.isEmpty() || vouchers.size() == 0) {
            view.printEmptyVouchers();
            return;
        }
        view.printVouchers(vouchers);
    }

    private CustomerDto getCustomerDto() {
        view.requestCustomerName();
        String customerName = view.getInput();
        view.requestEmail();
        String email = view.getInput();
        return new CustomerDto(customerName, email);
    }
}
