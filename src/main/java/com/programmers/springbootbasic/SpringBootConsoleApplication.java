package com.programmers.springbootbasic;

import com.programmers.springbootbasic.presentation.Command;
import com.programmers.springbootbasic.presentation.controller.VoucherController;
import com.programmers.springbootbasic.presentation.view.ConsoleApplicationView;
import com.programmers.springbootbasic.service.dto.VoucherResponse;
import com.programmers.springbootbasic.service.dto.VoucherResponses;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class SpringBootConsoleApplication implements CommandLineRunner {
    private final VoucherController voucherController;
    private final ConsoleApplicationView applicationView;

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
                applicationView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void create() throws IOException {
        applicationView.startCreation();

        String voucherType = applicationView.inputType();
        applicationView.printNewLine();
        String name = applicationView.inputName();
        applicationView.printNewLine();
        long minimumPriceCondition = applicationView.inputMinimumPriceCondition();
        applicationView.printNewLine();
        LocalDateTime expiredAt = applicationView.inputExpiredAt();
        applicationView.printNewLine();
        int amountOrPercent = applicationView.inputAmountOrPercent();
        applicationView.printNewLine();

        VoucherResponse response = voucherController.createVoucher(voucherType, name, minimumPriceCondition, expiredAt, amountOrPercent);
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
