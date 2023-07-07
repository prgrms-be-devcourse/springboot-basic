package com.programmers.springmission;

import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.OptionType;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.VoucherController;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ManagementController implements CommandLineRunner {

    private final Console console;
    private final VoucherController voucherController;

    public ManagementController(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    private boolean power = true;

    @Override
    public void run(String... args) {

        while (power) {
            try {
                console.outputOption();
                OptionType inputValue = OptionType.of(console.inputOption());

                switch (inputValue) {
                    case EXIT -> exitVoucherProgram();
                    case CREATE -> createVoucher();
                    case LIST -> loadVoucherList();
                }
            } catch (InvalidInputException | NumberFormatException invalidInputException) {
                console.output(invalidInputException.getMessage());
                log.info(invalidInputException.getMessage());
            }
        }
    }

    private void exitVoucherProgram() {
        console.outputExit();
        power = false;
    }

    private void createVoucher() {
        console.outputCreateOption();
        passToController();
    }

    private void passToController() {
        VoucherType inputType = VoucherType.of(console.inputOption());
        long inputAmount = Long.parseLong(console.inputVoucherAmount());

        VoucherCreateRequest voucherCreateRequest = new VoucherCreateRequest(inputType, inputAmount);
        console.outputCreate(voucherController.createVoucher(voucherCreateRequest));
    }

    private void loadVoucherList() {
        console.outputVoucherList(voucherController.findAllVoucher());
    }
}

