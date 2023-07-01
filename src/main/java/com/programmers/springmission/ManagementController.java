package com.programmers.springmission;

import com.programmers.springmission.global.exception.VoucherException;
import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.OptionType;
import com.programmers.springmission.voucher.domain.enums.VoucherType;
import com.programmers.springmission.voucher.presentation.VoucherController;
import com.programmers.springmission.voucher.presentation.request.VoucherCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ManagementController implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

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
            } catch (VoucherException voucherException) {
                console.output(voucherException.getMessage());
                logger.info(voucherException.getMessage());
            } catch (NumberFormatException numberFormatException) {
                logger.info(numberFormatException.getMessage());
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

