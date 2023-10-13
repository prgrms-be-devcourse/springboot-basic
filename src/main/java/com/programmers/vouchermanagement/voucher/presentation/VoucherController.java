package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {

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

        try {
            voucherType = VoucherType.getVoucherTypeByName(consoleInputManager.inputString().toLowerCase());
        } catch (VoucherTypeNotFoundException e) {
            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printDiscountRequest();
        Long discount = consoleInputManager.inputDiscount();

        try {
            voucherService.createVoucher(new VoucherRequestDto(voucherType, discount));
        } catch (IllegalDiscountException e) {
            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printSuccessCreation();
    }

    public void readAllVoucher() {

        consoleOutputManager.printList();

        List<VoucherResponseDto> voucherResponseDtos = voucherService.readAllVoucher();

        consoleOutputManager.printVoucherInfo(voucherResponseDtos);
    }
}
