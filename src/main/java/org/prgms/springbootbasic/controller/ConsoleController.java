package org.prgms.springbootbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.service.CustomerService;
import org.prgms.springbootbasic.service.CustomerVoucherManagementService;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.prgms.springbootbasic.console.Console.*;

@Controller
@Slf4j
public class ConsoleController {
    private static final String EXIT = "exit";
    private static final String LIST_VOUCHER = "listVoucher";
    private static final String LIST_CUSTOMER = "listCustomer";
    private static final String CREATE_VOUCHER = "createVoucher";
    private static final String CREATE_CUSTOMER = "createCustomer";
    private static final String BLACK = "black";
    private static final String WALLET = "wallet";
    private static final String ALLOCATE = "allocate";
    private static final String DELETE = "delete";
    private static final String SHOW_CUSTOMER_BY_VOUCHER = "showVoucherByCustomer";
    private static final String SHOW_VOUCHER_BY_CUSTOMER = "showCustomerByVoucher";
    private static final String BACK = "back";

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final CustomerVoucherManagementService managementService;

    public ConsoleController(VoucherService voucherService, CustomerService customerService, CustomerVoucherManagementService managementService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.managementService = managementService;
    }

    public void run() {
            String command = "";
            while (!command.equals(EXIT)) {
                try {
                    command = readCommand();

                    executeCommand(command);
                    success(command);
                } catch (IllegalArgumentException e) {
                    log.error("{}", e.toString());

                    printArgException();
                } catch (InputMismatchException e) {
                    String invalidVal = ignoreLine();

                    log.warn("User input = {}", invalidVal);
                    throw new IllegalArgumentException("Not integer.");
                } catch (NoSuchElementException e) {
                    log.error("input is exhausted");
                    throw new RuntimeException("Input is exhausted");
                } catch (IllegalStateException e) {
                    log.error("Scanner is closed");
                    throw new RuntimeException("Scanner is closed.");
                } catch (DuplicateKeyException e) {
                    log.error("Key duplicate error.", e);
                    printDuplicateKeyException();
                } catch (DataAccessException e) {
                    log.error("Database error.", e);
                    printRuntimeException();
                } catch (RuntimeException e) {
                    printRuntimeException();
                }
            }
    } // 프론트 컨트롤러 패턴에 대해. 공부해보자. 컨트롤러 일관성 없음. 왜 wallet만 따로 존재하는? 고민... -> 그냥 통일합시다.

    private void printDuplicateKeyException() {
        System.out.println("There is duplicate key in the values.");
    }

    private void executeCommand(String command) {
        switch (command) {
            case CREATE_VOUCHER -> createVoucher();
            case CREATE_CUSTOMER -> createCustomer();
            case LIST_VOUCHER -> listVoucher();
            case LIST_CUSTOMER -> listCustomer();
            case BLACK -> black();
            case WALLET -> runWallet();
            case EXIT -> {}
            default -> {
                log.warn("invalid command. now command = {}", command);
                throw new IllegalArgumentException("Invalid command. Type command again.");
            }
        }
    }

    private void createVoucher() {
        int voucherSeq = selectPolicyType();

        VoucherType voucherType = voucherService.seqToType(voucherSeq);

        int discountDegree = putDiscountDegree(voucherType);

        voucherService.upsert(voucherType, discountDegree);
    }

    private void createCustomer() {
        String[] info = putCustomerInfo().split(" ");

        if (info.length != 2) {
            throw new IllegalArgumentException("Customer info is not valid.");
        }

        String name = info[0];
        String email = info[1];

        customerService.insert(name, email);
    }

    private void listVoucher() {
        List<Voucher> vouchers = voucherService.findAll();

        printList(vouchers);
    }

    private void listCustomer() {
        List<Customer> customers = customerService.findAll();

        printList(customers);
    }

    private void black() {
        List<Customer> blacklist = customerService.findBlackAll();

        printList(blacklist);
    }


    private void runWallet() {
        String command = readWalletCommand();

        executeWalletCommand(command);
        success(command);
    }

    private void executeWalletCommand(String command) {
        switch (command){
            case ALLOCATE -> allocate();
            case DELETE -> delete();
            case SHOW_CUSTOMER_BY_VOUCHER -> showVoucherByCustomer();
            case SHOW_VOUCHER_BY_CUSTOMER -> showCustomerByVoucher();
            case BACK -> {}
            default -> {
                log.warn("invalid command. now command = {}", command);
                throw new IllegalArgumentException("Invalid command. Type command again.");
            }
        }
    }

    private void allocate() {
        UUID customerId = typeCustomerId();
        UUID voucherId = typeVoucherId();

        managementService.allocate(customerId, voucherId);
    }

    private void delete() {
        UUID customerId = typeCustomerId();
        UUID voucherId = typeVoucherId();

        managementService.delete(customerId, voucherId);
    }

    private void showVoucherByCustomer() {
        UUID customerId = typeCustomerId();
        List<Voucher> vouchers = managementService.searchVouchersFromCustomer(customerId);

        printList(vouchers);
    }

    private void showCustomerByVoucher() {
        UUID voucherId = typeVoucherId();
        List<Customer> customers = managementService.searchCustomerFromVoucher(voucherId);

        printList(customers);
    }
}
