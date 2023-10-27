package com.prgrms.voucher_manage.console;

import com.prgrms.voucher_manage.domain.customer.controller.CustomerController;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.entity.CustomerType;
import com.prgrms.voucher_manage.domain.voucher.controller.VoucherController;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.entity.VoucherType;
import com.prgrms.voucher_manage.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.console.MenuType.EXIT;
import static com.prgrms.voucher_manage.console.MenuType.matchMenuType;

@Component
@RequiredArgsConstructor
public class ConsoleManager implements ApplicationRunner {
    private final OutputUtil outputUtil;
    private final InputUtil inputUtil;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private static final Logger logger = LoggerFactory.getLogger(ConsoleManager.class);

    @Override
    public void run(ApplicationArguments args) {
        MenuType menuType = null;
        do {
            try {
                outputUtil.printMenu();
                menuType = matchMenuType(inputUtil.getStringInput());
                selectMenu(menuType);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

        } while (menuType != EXIT);
    }

    public void selectMenu(MenuType menuType) throws Exception {
        if (menuType == null) {
            throw new InvalidInputException("Invalid command input.");
        }

        switch (menuType) {
            case CREATE_VOUCHER -> createVoucher();
            case VOUCHER_LIST -> printVouchers(voucherController.getVouchers());
            case FIND_VOUCHER -> findVoucher();
            case UPDATE_VOUCHER -> updateVoucher();
            case DELETE_VOUCHER -> deleteVoucher();

            case SAVE_CUSTOMER -> saveCustomer();
            case BLACK_CUSTOMERS ->  printCustomers(customerController.getBlackCustomers());
            case ALL_CUSTOMERS -> printCustomers(customerController.getAllCustomers());
            case NORMAL_CUSTOMERS -> printCustomers(customerController.getNormalCustomers());
            case UPDATE_CUSTOMER -> updateCustomer();
            case FIND_CUSTOMER -> findCustomer();
        }
    }
    public void createVoucher() throws Exception {
        outputUtil.printVoucherSelect();
        VoucherType voucherType = VoucherType.matchVoucherType(inputUtil.getStringInput());
        if (voucherType == null) {
            throw new InvalidInputException("Invalid command input.");
        }
        switch (voucherType) {
            case FIXED -> outputUtil.requestDiscountPriceInfo();
            case PERCENT -> outputUtil.requestDiscountPercentInfo();
        }
        Long discountAmount = inputUtil.getLongInput();
        voucherController.createVoucher(voucherType, discountAmount);
    }

    private void findVoucher() throws IOException {
        outputUtil.printMessage("Input voucher id you want to find");
        UUID voucherId = UUID.fromString(inputUtil.getStringInput());
        Voucher voucher = voucherController.findVoucher(voucherId);
        switch (voucher.getType()) {
            case FIXED -> outputUtil.printFixedVoucherInfo(voucher);
            case PERCENT -> outputUtil.printPercentVoucherInfo(voucher);
        }
    }

    public void updateVoucher() throws Exception {
        outputUtil.printMessage("Input voucher id you want to update.");
        UUID voucherId = UUID.fromString(inputUtil.getStringInput());
        outputUtil.printMessage("Input voucher price to update");
        Long discountAmount = inputUtil.getLongInput();
        voucherController.updateVoucher(voucherId,discountAmount);
    }

    public void deleteVoucher() throws IOException {
        outputUtil.printMessage("Input voucherId to delete.");
        voucherController.deleteVoucher(UUID.fromString(inputUtil.getStringInput()));
    }

    private CustomerType getCustomerType() throws IOException {
        CustomerType customerType = CustomerType.matchTypeByString(inputUtil.getStringInput());
        System.out.println(customerType);
        if (customerType == null){
            throw new InvalidInputException("Invalid command input");
        }
        return customerType;
    }

    private void printVouchers(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> {
            switch (voucher.getType()) {
                case FIXED -> outputUtil.printFixedVoucherInfo(voucher);
                case PERCENT -> outputUtil.printPercentVoucherInfo(voucher);
            }
        });
    }

    public void saveCustomer() throws IOException {
        outputUtil.printCustomerSelect();
        CustomerType customerType = getCustomerType();
        outputUtil.printMessage("Input customer name.");
        String name = inputUtil.getStringInput();
        customerController.save(new Customer(name, customerType));
    }

    public void findCustomer() throws IOException {
        outputUtil.printMessage("Input customer name to find.");
        String name = inputUtil.getStringInput();
        Customer foundCustomer = customerController.findByName(name);
        outputUtil.printCustomerInfo(foundCustomer);
    }

    public void updateCustomer() throws Exception {
        outputUtil.printMessage("Input customer id to update.");
        UUID id = inputUtil.getUUIDInput();
        Customer customer = customerController.findById(id);

        outputUtil.printMessage("Input customer name to change");
        String name = inputUtil.getStringInput();
        customerController.update(new Customer(customer.getId(),name, customer.getType()));
    }

    private void printCustomers(List<Customer> customers) {
        customers.forEach(outputUtil::printCustomerInfo);
    }
}
