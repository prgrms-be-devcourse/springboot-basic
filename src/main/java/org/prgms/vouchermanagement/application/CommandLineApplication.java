package org.prgms.vouchermanagement.application;

import org.prgms.vouchermanagement.io.Console;
import org.prgms.vouchermanagement.service.VoucherService;
import org.prgms.vouchermanagement.voucher.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class CommandLineApplication implements CommandLineRunner, ApplicationContextAware {

    private final Console console;
    private final VoucherService voucherService;
    private ApplicationContext applicationContext;

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) {
        while (true) {
            CommandMenu currentCommand = CommandMenu.START;

            console.printCommandMenu();
            try {
                currentCommand = CommandMenu.getCommandMenu(console.getCommand());
                switch (currentCommand) {
                    case EXIT -> { return; }
                    case CREATE_NEW_VOUCHER -> selectNewVoucher();
                    case SHOW_VOUCHER_LIST -> showVoucherList();
                    case SHOW_BLACK_LIST -> showBlackList();
                }
            } catch (RuntimeException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void selectNewVoucher() {
        long amountOrPercent = 0;
        VoucherType voucherType;

        console.printSelectVoucherType();
        voucherType = VoucherType.getVoucherType(console.getVoucherTypeInput());

        if (voucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            amountOrPercent = getFixedVoucherAmount();
        }

        else if (voucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            amountOrPercent = getPercentDiscount();
        }

        voucherService.createNewVoucher(voucherType, amountOrPercent);
        console.printSavedFinished();
    }

    private long getPercentDiscount() {
        console.printGetPercentDiscount();
        return console.getPercentDiscount();
    }

    public long getFixedVoucherAmount() {
        console.printGetFixedVoucherAmount();
        return console.getFixedVoucherAmount();
    }

    public void showVoucherList() {
        VoucherType listVoucherType;

        console.printSelectVoucherListType();
        listVoucherType = VoucherType.getVoucherType(console.getVoucherTypeInput());
        Optional<Map<UUID, Voucher>> voucherList = voucherService.getVoucherList(listVoucherType);
        console.printVoucherList(voucherList, listVoucherType);
    }

    private void showBlackList() throws IOException {
        Resource resource = applicationContext.getResource("customer_blacklist.csv");
        console.printCustomerBlackList(resource.getFile().toPath().toString());
    }

}
