package com.programmers.springbootbasic;

import com.programmers.springbootbasic.presentation.Command;
import com.programmers.springbootbasic.presentation.controller.VoucherController;
import com.programmers.springbootbasic.presentation.view.ConsoleApplicationView;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponse;
import com.programmers.springbootbasic.service.dto.Voucher.VoucherResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Profile("console")
@Component
public class SpringBootConsoleApplication implements CommandLineRunner {
    private final VoucherController voucherController;
    private final ConsoleApplicationView applicationView;
    private final Logger logger = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public SpringBootConsoleApplication(VoucherController voucherController, ConsoleApplicationView applicationView) {
        this.voucherController = voucherController;
        this.applicationView = applicationView;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            try {
                Command command = applicationView.selectCommand();
                switch (command) {
                    case CREATE -> create();
                    case LIST -> list();
                    case EXIT -> {
                        exit();
                        return;
                    }
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                logger.error(e.getMessage(), e);
                applicationView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void create() throws IOException {
        applicationView.startCreation();

        String voucherType = applicationView.inputType();
        int amountOrPercent = applicationView.inputAmountOrPercent();

        VoucherResponse response = voucherController.createVoucher(voucherType, amountOrPercent);
        applicationView.printCreatedVoucher(response);
    }

    private void list() throws IOException {
        VoucherResponses voucherResponses = voucherController.listVouchers();
        applicationView.listVouchers(voucherResponses);
    }

    private void exit() throws IOException {
        applicationView.exit();
    }
}
