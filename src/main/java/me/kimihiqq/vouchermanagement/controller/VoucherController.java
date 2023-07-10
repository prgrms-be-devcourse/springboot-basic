package me.kimihiqq.vouchermanagement.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.domain.customer.dto.CustomerDto;
import me.kimihiqq.vouchermanagement.domain.customer.service.CustomerService;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import me.kimihiqq.vouchermanagement.domain.voucher.dto.VoucherDto;
import me.kimihiqq.vouchermanagement.domain.voucher.service.VoucherService;
import me.kimihiqq.vouchermanagement.domain.voucherwallet.service.VoucherWalletService;
import me.kimihiqq.vouchermanagement.io.Console;
import me.kimihiqq.vouchermanagement.option.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController {
    private final Console console;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final VoucherWalletService voucherWalletService;

    @Override
    public void run() {
        while (true) {
            try {
                MainMenuOption mainMenuOption = console.promptUserChoice(MainMenuOption.class);

                switch (mainMenuOption) {
                    case EXIT:
                        return;
                    case MANAGE_VOUCHERS:
                        manageVouchers();
                        break;
                    case MANAGE_CUSTOMERS:
                        manageCustomers();
                        break;
                }
            } catch (Exception e) {
                log.error("Error reading input", e);

                console.printLine("입력을 읽는 중 오류가 발생했습니다.");
            }
        }
    }

    private void manageVouchers() {
        while (true) {
            try {
                VoucherMenuOption voucherMenuOption = console.promptUserChoice(VoucherMenuOption.class);

                switch (voucherMenuOption) {
                    case RETURN_MAIN_MENU:
                        return;
                    case CREATE_VOUCHER:
                        VoucherTypeOption voucherTypeOption = console.promptUserChoice(VoucherTypeOption.class);
                        long discount = console.readDiscount("Enter discount amount or rate: ");

                        VoucherDto voucherDto = new VoucherDto(voucherTypeOption.name(), discount);
                        Voucher createdVoucher = voucherService.createVoucher(voucherDto);

                        console.printLine(createdVoucher.getVoucherId() + ": " + createdVoucher.getType() + " - " + createdVoucher.getDiscount());
                        break;
                    case LIST_VOUCHERS:
                        voucherService.listVouchers().forEach(voucher ->
                                console.printLine(voucher.getVoucherId() + ": " + voucher.getType() + " - " + voucher.getDiscount())
                        );
                        break;
                    case LIST_CUSTOMERS_WITH_VOUCHER:
                        UUID voucherId = UUID.fromString(console.readLineWithPrompt("Enter voucher ID: "));
                        List<Customer> customers = customerService.findCustomersWithVoucher(voucherId);

                        customers.forEach(customer ->
                                console.printLine(customer.getId() + ": " + customer.getName() + " - " + customer.getEmail())
                        );
                        break;
                }
            } catch (Exception e) {
                log.error("Error reading input", e);

                console.printLine("입력을 읽는 중 오류가 발생했습니다.");
            }
        }
    }

    private void manageCustomers() {
        while (true) {
            try {
                CustomerMenuOption customerMenuOption = console.promptUserChoice(CustomerMenuOption.class);

                switch (customerMenuOption) {
                    case RETURN_MAIN_MENU:
                        return;
                    case CREATE_CUSTOMER:
                        String name = console.readLineWithPrompt("Enter customer name: ");
                        String email = console.readLineWithPrompt("Enter customer email: ");

                        CustomerDto customerDto = new CustomerDto(name, email, CustomerStatus.WHITE);
                        Customer createdCustomer = customerService.createCustomer(customerDto);

                        console.printLine(createdCustomer.getId() + ": " + createdCustomer.getName() + " - " + createdCustomer.getEmail());
                        break;
                    case LIST_CUSTOMERS:
                        customerService.listCustomers().forEach(customer ->
                                console.printLine(customer.getId() + ": " + customer.getName() + " - " + customer.getEmail())
                        );
                        break;
                    case LIST_ALL_VOUCHERS_BY_CUSTOMER:
                        UUID customerId = UUID.fromString(console.readLineWithPrompt("Enter customer ID: "));
                        Set<Voucher> vouchers = voucherWalletService.findVouchersByCustomerId(customerId);

                        vouchers.forEach(voucher ->
                                console.printLine(voucher.getVoucherId() + ": " + voucher.getType() + " - " + voucher.getDiscount())
                        );
                        break;
                    case UPDATE_CUSTOMER_STATUS:
                        customerId = UUID.fromString(console.readLineWithPrompt("Enter customer ID: "));
                        CustomerStatus status = CustomerStatus.valueOf(console.readLineWithPrompt("Enter new customer status: "));

                        Customer customer = customerService.findCustomerById(customerId)
                                .orElseThrow(() -> new RuntimeException("Customer not found"));

                        customer.updateCustomerStatus(status);
                        customerService.updateCustomerStatus(customer);  // Update the customer's status in the database

                        console.printLine("Updated customer status to " + status);
                        break;
                    case ADD_VOUCHER_TO_CUSTOMER:
                        customerId = UUID.fromString(console.readLineWithPrompt("Enter customer ID: "));
                        UUID voucherId = UUID.fromString(console.readLineWithPrompt("Enter voucher ID: "));

                        voucherWalletService.addVoucherToWallet(customerId, voucherId);
                        console.printLine("Added voucher " + voucherId + " to customer " + customerId);
                        break;

                    case REMOVE_VOUCHER_FROM_CUSTOMER:
                        customerId = UUID.fromString(console.readLineWithPrompt("Enter customer ID: "));
                        voucherId = UUID.fromString(console.readLineWithPrompt("Enter voucher ID: "));

                        voucherWalletService.removeVoucherFromWallet(customerId, voucherId);
                        console.printLine("Removed voucher " + voucherId + " from customer " + customerId);
                        break;
                }
            } catch (Exception e) {
                log.error("Error reading input", e);

                console.printLine("입력을 읽는 중 오류가 발생했습니다.");
            }
        }
    }
}