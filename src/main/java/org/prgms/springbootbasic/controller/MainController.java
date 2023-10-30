package org.prgms.springbootbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.console.Console;
import org.prgms.springbootbasic.common.console.MainConsole;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.service.CustomerService;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@Slf4j
public class MainController {
    private static final String EXIT = "exit";
    private static final String LIST = "list";
    private static final String CREATE = "create";
    private static final String BLACK = "black";
    private static final String WALLET = "wallet";

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletController walletController;

    public MainController(VoucherService voucherService, CustomerService customerService, WalletController walletController) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletController = walletController;
    }

    public void run() {
            String command = "";
            while (!command.equals(EXIT)) {
                try {
                    command = MainConsole.readCommand();

                    executeCommand(command);
                } catch (InputMismatchException e) {
                    String invalidVal = MainConsole.ignoreLine();

                    log.warn("User input = {}", invalidVal);
                    throw new IllegalArgumentException("Not integer.");
                } catch (NoSuchElementException e) {
                    log.error("input is exhausted");
                    throw new RuntimeException("Input is exhausted");
                } catch (IllegalStateException e) {
                    log.error("Scanner is closed");
                    throw new RuntimeException("Scanner is closed.");
                } catch (IllegalArgumentException e) {
                    MainConsole.printArgException();
                } catch (DataAccessException e) {
                    log.error("Database error.", e);
                    MainConsole.printRuntimeException();
                } catch (RuntimeException e) {
                    MainConsole.printRuntimeException();
                }
            }
    }

    private void executeCommand(String command) {
        switch (command) {
            case CREATE -> create();
            case LIST -> list();
            case BLACK -> black();
            case WALLET -> wallet();
            case EXIT -> {}
            default -> {
                log.warn("invalid command. now command = {}", command);
                throw new IllegalArgumentException("Invalid command. Type command again.");
            }
        }
    }

    private void create(){
        int voucherSeq = MainConsole.selectCreateType();

        VoucherType voucherType = voucherService.seqToType(voucherSeq);

        int discountDegree = MainConsole.putDiscountDegree(voucherType);

        voucherService.create(voucherType, discountDegree);
    }

    private void list(){
        List<VoucherPolicy> voucherPolicies = voucherService.findAll();

        Console.printList(voucherPolicies);
    }

    private void black(){
        List<Customer> blacklist = customerService.findBlackAll();

        Console.printList(blacklist);
    }

    private void wallet(){
        walletController.run();
    }
}
