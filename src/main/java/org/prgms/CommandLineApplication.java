package org.prgms;

import org.prgms.customer.Customer;
import org.prgms.io.FileReader;
import org.prgms.io.InOut;
import org.prgms.voucher.domain.FixedAmountVoucher;
import org.prgms.voucher.domain.PercentDiscountVoucher;
import org.prgms.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(
            VoucherService service,
            InOut console,
            FileReader fileReader
    ) {

        this.service = service;
        this.console = console;
        this.fileReader = fileReader;
        this.blackList = new ArrayList<>();

    }

    public void run() throws Exception {
        this.readBlackList();
        this.execute();
    }

    private void execute() {
        while (true) {
            console.optionMessage();
            String inputText = console.input();
            try {
                switch (inputText) {
                    case "exit":
                        return;
                    case "create":
                        int opt = console.chooseVoucher();
                        switch (opt) {
                            case 1 -> service.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10L));
                            case 2 -> service.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10L));
                            default -> throw new IllegalArgumentException(String.valueOf(opt));
                        }
                        break;
                    case "list":
                        service.listVoucher();
                        break;
                    default:
                        throw new IllegalArgumentException(inputText);
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                console.inputError(e.getMessage());
            }
        }
    }

    private void readBlackList() throws Exception {
        blackList.addAll(fileReader.readFile());
        logger.info("고객 블랙리스트 : {}", blackList);
    }
}
