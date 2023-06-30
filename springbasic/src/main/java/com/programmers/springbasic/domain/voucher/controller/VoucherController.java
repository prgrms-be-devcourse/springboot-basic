package com.programmers.springbasic.domain.voucher.controller;

import com.programmers.springbasic.domain.voucher.view.IOConsole;
import com.programmers.springbasic.domain.voucher.dto.request.CommandDTO;
import com.programmers.springbasic.domain.voucher.dto.request.CreateFixedAmountVoucherRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.CreatePercentDiscountVoucherRequestDTO;
import com.programmers.springbasic.domain.voucher.dto.request.VoucherOptionDTO;
import com.programmers.springbasic.domain.voucher.model.CommandOption;
import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import com.programmers.springbasic.domain.voucher.service.VoucherService;
import com.programmers.springbasic.domain.voucher.view.VoucherCreateMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;
    private final IOConsole ioConsole = new IOConsole();

    private boolean keepGoingFlag = true;

    public void run() throws IOException {
        try {
            while (keepGoingFlag) {
                ioConsole.showMenu();

                String inputCommand = ioConsole.getInput();
                CommandDTO commandDTO = new CommandDTO(inputCommand);

                executeCommand(commandDTO);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            ioConsole.closeIOResource();
        }
    }

    private void executeCommand(CommandDTO inputCommandDTO) throws Exception {
        String command = inputCommandDTO.getInputCommand();

        if (command.equals(CommandOption.CREATE.getCommand())) {
            ioConsole.printSingleOutput(VoucherCreateMessage.VOUCHER_OPTION_MESSAGE.getMessage());

            String inputVoucherOption = ioConsole.getInput();
            VoucherOptionDTO voucherOptionDTO = new VoucherOptionDTO(inputVoucherOption);

            handleCreateVoucher(voucherOptionDTO); // create voucher
            return;
        }

        if (command.equals(CommandOption.LIST.getCommand())) {
            ioConsole.printSingleOutput(VoucherCreateMessage.VOUCHER_OPTION_MESSAGE.getMessage());

            String inputVoucherOption = ioConsole.getInput();
            VoucherOptionDTO voucherOptionDTO = new VoucherOptionDTO(inputVoucherOption);

            handleListVoucher(voucherOptionDTO);    // list voucher
            return;
        }

        if (command.equals(CommandOption.EXIT.getCommand())) {
            keepGoingFlag = false;
        }
    }

    private void handleCreateVoucher(VoucherOptionDTO voucherOptionDTO) throws IOException {
        String voucherOption = voucherOptionDTO.getVoucherOption();

        if (voucherOption.equals(VoucherOption.FIXED_AMOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printSingleOutput(VoucherCreateMessage.FIXED_AMOUNT_INPUT_MESSAGE.getMessage());
            String fixedAmountInput = ioConsole.getInput();
            CreateFixedAmountVoucherRequestDTO createFixedAmountVoucherRequestDTO = new CreateFixedAmountVoucherRequestDTO(fixedAmountInput);

            voucherService.createFixedAmountVoucher(createFixedAmountVoucherRequestDTO);
            ioConsole.printSingleOutput(VoucherCreateMessage.FIXED_AMOUNT_VOUCHER_CREATE_MESSAGE.getMessage());
        }

        if (voucherOption.equals(VoucherOption.PERCENT_DISCOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printSingleOutput(VoucherCreateMessage.PERCENT_DISCOUNT_INPUT_MESSAGE.getMessage());
            String percentDiscountInput = ioConsole.getInput();
            CreatePercentDiscountVoucherRequestDTO createPercentDiscountVoucherRequestDTO = new CreatePercentDiscountVoucherRequestDTO(percentDiscountInput);

            voucherService.createPercentDiscountVoucher(createPercentDiscountVoucherRequestDTO);
            ioConsole.printSingleOutput(VoucherCreateMessage.PERCENT_DISCOUNT_VOUCHER_CREATE_MESSAGE.getMessage());
        }
    }

    private void handleListVoucher(VoucherOptionDTO voucherOptionDTO) throws IOException {
        String voucherOption = voucherOptionDTO.getVoucherOption();

        if (voucherOption.equals(VoucherOption.FIXED_AMOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printListOutput(voucherService.getAllFixedAmountVoucherInfo());
        }

        if (voucherOption.equals(VoucherOption.PERCENT_DISCOUNT_VOUCHER.getVoucherOption())) {
            ioConsole.printListOutput(voucherService.getAllPercentDiscountVoucherInfo());
        }
    }
}
