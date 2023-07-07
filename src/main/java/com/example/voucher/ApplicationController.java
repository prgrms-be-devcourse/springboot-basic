package com.example.voucher;

import static com.example.voucher.utils.ExceptionMessage.*;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.example.voucher.constant.ModeType;
import com.example.voucher.controller.VoucherController;
import com.example.voucher.controller.VoucherCreateRequest;

import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.io.Console;
import com.example.voucher.utils.ExceptionHandler;
import com.example.voucher.utils.Validator;

@Controller
public class ApplicationController implements CommandLineRunner {

    private boolean isOn = true;

    private final Console console;
    private final VoucherController voucherController;

    private ApplicationController(VoucherController voucherController) {
        this.console = new Console();
        this.voucherController = voucherController;

    }

    @Override
    public void run(String... args) throws Exception {
        while (isOn) {
            ModeType selectedModeType = getSelectedType();

            if (selectedModeType == null) {
                continue;
            }

            decideProcess(selectedModeType);
        }

    }

    private ModeType getSelectedType() {
        console.requestModeTypeSelection();

        try {
            String input = console.readModeType();
            ModeType selectedModeType = Validator.validateModeTypeMatch(input);

            return selectedModeType;

        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleException(new IllegalArgumentException(INVALID_ARGUMENT_RETRY_MODE_TYPE_SELECTION));

            return null;
        }

    }

    private void decideProcess(ModeType selectedModeType) {
        switch (selectedModeType) {
            case EXIT -> processExit();
            case CREATE -> processCreate();
            case LIST -> processList();
        }

    }

    private void processExit() {
        isOn = false;
    }

    private void processCreate() {
        console.requestVoucherInfo();

        try {
            console.requestVoucherTypeSelection();
            Integer voucherType = console.readVoucherType();

            console.requestDiscountValue();
            Long discountValue = console.readDiscountValue();

            VoucherCreateRequest request = new VoucherCreateRequest(voucherType, discountValue);
            voucherController.createVoucher(request);

        } catch (IllegalArgumentException e) {
            ExceptionHandler.handleException(new IllegalArgumentException(INVALID_ARGUMENT_CANT_CREATE));
        }

    }

    private void processList() {

        List<VoucherDTO> VoucherDTOs = voucherController.getVouchers();

        for (VoucherDTO dto : VoucherDTOs) {
            console.printVoucherInfo(dto);
        }

    }

}
