package org.prgms;

import org.prgms.customer.Customer;
import org.prgms.io.FileReader;
import org.prgms.io.InOut;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.PercentDiscountVoucher;
import org.prgms.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication {
    private final VoucherService service;
    private final InOut console;
    private final FileReader fileReader;
    private final List<Customer> blackList;

    @Autowired
    private ApplicationContext context;

    public CommandLineApplication(VoucherService service, InOut console, FileReader fileReader) {
        this.service = service;
        this.console = console;
        this.fileReader = fileReader;
        this.blackList = new ArrayList<>();
    }

    public void execute() {
        while (true) {
            console.optionMessage();
            String inputText = console.input();
            try {
                switch (inputText) {
                    case "exit":
                        return;
                    case "create":
                        switch (console.chooseVoucher()) {
                            case 1 -> service.createVoucher(new FixedAmountVoucher(10L, UUID.randomUUID()));
                            case 2 -> service.createVoucher(new PercentDiscountVoucher(10L, UUID.randomUUID()));
                            default -> throw new IllegalArgumentException();
                        }
                        break;
                    case "list":
                        service.listVoucher();
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                console.inputError();
            }
        }
    }

    public void readBlackList(String path) throws Exception {
        blackList.addAll(fileReader.readFile(context.getResource(path).getFile()));
        System.out.println(blackList);
    }
}
