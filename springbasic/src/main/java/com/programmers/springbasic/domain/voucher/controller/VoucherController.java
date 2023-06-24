package com.programmers.springbasic.domain.voucher.controller;

import com.programmers.springbasic.domain.voucher.dto.request.CommandDTO;
import com.programmers.springbasic.domain.voucher.dto.request.CreateFixedAmountVoucherRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.CreatePercentDiscountVoucherRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.VoucherOptionDTO;
import com.programmers.springbasic.domain.voucher.model.CommandOption;
import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private static final String VOUCHER_OPTION_MESSAGE = "input F or P(F: Fixed, P: Percent)";
    private static final String FIXED_AMOUNT_INPUT_MESSAGE = "input fixed amount";
    private static final String FIXED_AMOUNT_VOUCHER_CREATE_MESSAGE = "Fixed Amount Voucher Created!!";
    private static final String PERCENT_DISCOUNT_INPUT_MESSAGE = "input percent discount";
    private static final String PERCENT_DISCOUNT_VOUCHER_CREATE_MESSAGE = "Percent Discount Voucher Created!!";

    private final VoucherService voucherService;
    private final IOController ioController;

    private boolean keepGoingFlag = true;

    public void run() throws IOException {
        try {
            while (keepGoingFlag) {
                ioController.showMenu();

                String inputCommand = ioController.getInput();
                CommandDTO commandDTO = new CommandDTO(inputCommand);

                executeCommand(commandDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ioController.closeIOResource();
        }
    }

    private void executeCommand(CommandDTO inputCommandDTO) throws Exception {
        String command = inputCommandDTO.getInputCommand();

        if (command.equals(CommandOption.CREATE.toString())) {
            ioController.printSingleOutput(VOUCHER_OPTION_MESSAGE);

            String inputVoucherOption = ioController.getInput();
            VoucherOptionDTO voucherOptionDTO = new VoucherOptionDTO(inputVoucherOption);

            handleCreateVoucher(voucherOptionDTO); // create voucher
            return;
        }

        if (command.equals(CommandOption.LIST.toString())) {
            ioController.printSingleOutput(VOUCHER_OPTION_MESSAGE);

            String inputVoucherOption = ioController.getInput();
            VoucherOptionDTO voucherOptionDTO = new VoucherOptionDTO(inputVoucherOption);

            handleListVoucher(voucherOptionDTO);    // list voucher
            return;
        }

        if (command.equals(CommandOption.EXIT.toString())) {
            keepGoingFlag = false;
        }
    }

    private void handleCreateVoucher(VoucherOptionDTO voucherOptionDTO) throws IOException {
        String voucherOption = voucherOptionDTO.getVoucherOption();

        if (voucherOption.equals(VoucherOption.FIXED_AMOUNT_VOUCHER.toString())) {
            ioController.printSingleOutput(FIXED_AMOUNT_INPUT_MESSAGE);
            String fixedAmountInput = ioController.getInput();
            CreateFixedAmountVoucherRequestDTO createFixedAmountVoucherRequestDTO = new CreateFixedAmountVoucherRequestDTO(fixedAmountInput);

            double fixedAmount = getFixedAmountFromInput(createFixedAmountVoucherRequestDTO);
            voucherService.createFixedAmountVoucher(fixedAmount);
            ioController.printSingleOutput(FIXED_AMOUNT_VOUCHER_CREATE_MESSAGE);
        }

        if (voucherOption.equals(VoucherOption.PERCENT_DISCOUNT_VOUCHER.toString())) {
            ioController.printSingleOutput(PERCENT_DISCOUNT_INPUT_MESSAGE);
            String percentDiscountInput = ioController.getInput();
            CreatePercentDiscountVoucherRequestDTO createPercentDiscountVoucherRequestDTO = new CreatePercentDiscountVoucherRequestDTO(percentDiscountInput);

            double percentDiscount = getPercentDiscountFromInput(createPercentDiscountVoucherRequestDTO);
            voucherService.createPercentDiscountVoucher(percentDiscount);
            ioController.printSingleOutput(PERCENT_DISCOUNT_VOUCHER_CREATE_MESSAGE);
        }
    }

    private double getFixedAmountFromInput(CreateFixedAmountVoucherRequestDTO createFixedAmountVoucherRequestDTO) {
        return Double.parseDouble(createFixedAmountVoucherRequestDTO.getInputFixedAmount());
    }

    private double getPercentDiscountFromInput(CreatePercentDiscountVoucherRequestDTO createPercentDiscountVoucherRequestDTO) {
        return Double.parseDouble(createPercentDiscountVoucherRequestDTO.getInputPercent());
    }
}
