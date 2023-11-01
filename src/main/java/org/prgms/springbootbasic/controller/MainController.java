package org.prgms.springbootbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.console.Console;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.service.CustomerService;
import org.prgms.springbootbasic.service.VoucherService;
import org.prgms.springbootbasic.service.WalletService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.prgms.springbootbasic.common.console.Console.*;
import static org.prgms.springbootbasic.common.console.Console.typeVoucherId;

@Controller
@Slf4j
public class MainController {
    private static final String EXIT = "exit";
    private static final String LIST = "list";
    private static final String CREATE = "create";
    private static final String BLACK = "black";
    private static final String WALLET = "wallet";
    private static final String ALLOCATE = "allocate";
    private static final String DELETE = "delete";
    private static final String SHOW_CUSTOMER_BY_VOUCHER = "showVoucherByCustomer";
    private static final String SHOW_VOUCHER_BY_CUSTOMER = "showCustomerByVoucher";
    private static final String BACK = "back";

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;

    public MainController(VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    public void run() {
            String command = "";
            while (!command.equals(EXIT)) {
                try {
                    command = Console.readCommand();

                    executeCommand(command);
                } catch (InputMismatchException e) {
                    String invalidVal = Console.ignoreLine();

                    log.warn("User input = {}", invalidVal);
                    throw new IllegalArgumentException("Not integer.");
                } catch (NoSuchElementException e) {
                    log.error("input is exhausted");
                    throw new RuntimeException("Input is exhausted");
                } catch (IllegalStateException e) {
                    log.error("Scanner is closed");
                    throw new RuntimeException("Scanner is closed.");
                } catch (IllegalArgumentException e) {
                    Console.printArgException();
                } catch (DataAccessException e) {
                    log.error("Database error.", e);
                    Console.printRuntimeException();
                } catch (RuntimeException e) {
                    Console.printRuntimeException();
                }
            }
    } // 프론트 컨트롤러 패턴에 대해. 공부해보자. 컨트롤러 일관성 없음. 왜 wallet만 따로 존재하는? 고민... -> 그냥 통일합시다.

    private void executeCommand(String command) {
        switch (command) {
            case CREATE -> create();
            case LIST -> list();
            case BLACK -> black();
            case WALLET -> runWallet();
            case EXIT -> {}
            default -> {
                log.warn("invalid command. now command = {}", command);
                throw new IllegalArgumentException("Invalid command. Type command again.");
            }
        }
    }

    private void create(){
        int voucherSeq = Console.selectCreateType();

        VoucherType voucherType = voucherService.seqToType(voucherSeq);

        int discountDegree = Console.putDiscountDegree(voucherType);

        voucherService.create(voucherType, discountDegree);
    }

    private void list(){
        List<Voucher> vouchers = voucherService.findAll();

        Console.printList(vouchers);
    }

    private void black(){
        List<Customer> blacklist = customerService.findBlackAll();

        Console.printList(blacklist);
    }


    private void runWallet(){
        String command = readWalletCommand();

        executeWalletCommand(command);
        success(command);
    }

    private void executeWalletCommand(String command){
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

    private void allocate(){
        UUID customerId = typeCustomerId();
        UUID voucherId = typeVoucherId();

        walletService.allocate(customerId, voucherId);
    }

    private void delete(){
        UUID customerId = typeCustomerId();
        UUID voucherId = typeVoucherId();

        walletService.delete(customerId, voucherId);
    }

    private void showVoucherByCustomer(){
        UUID customerId = typeCustomerId();
        List<Voucher> vouchers = walletService.searchVouchersFromCustomer(customerId);

        Console.printList(vouchers);
    }

    private void showCustomerByVoucher(){
        UUID voucherId = typeVoucherId();
        List<Customer> customers = walletService.searchCustomerFromVoucher(voucherId);

        Console.printList(customers);
    }
}
