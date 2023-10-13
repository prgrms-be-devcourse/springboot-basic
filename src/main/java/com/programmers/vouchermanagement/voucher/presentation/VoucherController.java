package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.exception.FileIOException;
import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherController.class);

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final VoucherService voucherService;

    public VoucherController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, VoucherService voucherService) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.voucherService = voucherService;
    }

    public void createVoucher() {

        consoleOutputManager.printVoucherTypeMenu();

        VoucherType voucherType;
        String input = consoleInputManager.inputString().toLowerCase();

        try {
            voucherType = VoucherType.getVoucherTypeByName(input);

        } catch (VoucherTypeNotFoundException e) {
            LOGGER.error(e.getMessage() + "Console Input : " + input);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printDiscountRequest();
        Long discount = consoleInputManager.inputDiscount();

        try {
            voucherService.createVoucher(new VoucherRequestDto(voucherType, discount));

        } catch (IllegalDiscountException e) {
            LOGGER.error(e.getMessage() + "Console Input : " + discount);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (FileIOException e) {
            LOGGER.error(e.getMessage());

            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printSuccessCreation();
    }

    public void readAllVoucher() {

        consoleOutputManager.printList();

        try {
            List<VoucherResponseDto> voucherResponseDtos = voucherService.readAllVoucher();
            consoleOutputManager.printVoucherInfo(voucherResponseDtos);

        } catch (VoucherNotFoundException | FileIOException | VoucherTypeNotFoundException e) {
            LOGGER.error(e.getMessage());

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }
}
