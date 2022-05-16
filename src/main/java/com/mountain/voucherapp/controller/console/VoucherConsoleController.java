package com.mountain.voucherapp.controller.console;

import com.mountain.voucherapp.common.io.InputConsole;
import com.mountain.voucherapp.common.io.OutputConsole;
import com.mountain.voucherapp.dto.VoucherCreateDto;
import com.mountain.voucherapp.dto.VoucherIdUpdateDto;
import com.mountain.voucherapp.model.enums.DiscountPolicy;
import com.mountain.voucherapp.model.vo.voucher.Voucher;
import com.mountain.voucherapp.service.VoucherAppService;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

import static com.mountain.voucherapp.common.constants.ErrorMessage.WRONG_VALUE;
import static com.mountain.voucherapp.common.constants.ProgramMessage.*;

@Controller
public class VoucherConsoleController {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherAppService voucherAppService;

    public VoucherConsoleController(InputConsole inputConsole,
                                    OutputConsole outputConsole,
                                    VoucherAppService voucherAppService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherAppService = voucherAppService;
    }

    public void create() {
        outputConsole.choiceDiscountPolicy();
        try {
            int policyOrdinal = Integer.parseInt(inputConsole.input());
            Optional<DiscountPolicy> policyOptional = DiscountPolicy.find(policyOrdinal);
            if (policyOptional.isEmpty())
                throw new IllegalArgumentException(WRONG_VALUE.getMessage());
            DiscountPolicy discountPolicy = policyOptional.get();
            outputConsole.printMessage(PLEASE_AMOUNT);
            long discountAmount = Long.parseLong(inputConsole.input());
            Voucher voucher = discountPolicy.getVoucher();
            if (voucher.validate(discountAmount)) {
                voucherAppService.create(new VoucherCreateDto(discountPolicy, discountAmount));
            }
        } catch (NumberFormatException numberFormatException) {
            throw numberFormatException;
        }
    }

    public void showVoucherList() {
        outputConsole.printVoucherList(voucherAppService.showVoucherList());
    }

    public void showCustomerVoucherInfo() {
        outputConsole.printCustomerVoucherInfo(voucherAppService.showCustomerVoucherInfo());
    }

    public void exit() {
        outputConsole.close();
    }

    public void addVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_CUSTOMER_ID);
        UUID customerId = UUID.fromString(inputConsole.input());
        outputConsole.printMessage(PLEASE_INPUT_VOUCHER_ID);
        UUID voucherId = UUID.fromString(inputConsole.input());
        voucherAppService.addVoucher(new VoucherIdUpdateDto(customerId, voucherId));
    }

    public void removeVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_CUSTOMER_ID);
        UUID customerId = UUID.fromString(inputConsole.input());
        voucherAppService.removeVoucher(customerId);
    }

    public void showByVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_VOUCHER_ID);
        UUID voucherId = UUID.fromString(inputConsole.input());
        outputConsole.printCustomerVoucherInfo(voucherAppService.showByVoucher(voucherId));
    }
}
