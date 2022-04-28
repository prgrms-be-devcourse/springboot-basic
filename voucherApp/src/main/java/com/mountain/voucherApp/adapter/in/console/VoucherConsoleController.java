package com.mountain.voucherApp.adapter.in.console;

import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.port.in.VoucherCreateDto;
import com.mountain.voucherApp.application.port.in.VoucherIdUpdateDto;
import com.mountain.voucherApp.domain.Voucher;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import com.mountain.voucherApp.shared.io.InputConsole;
import com.mountain.voucherApp.shared.io.OutputConsole;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.mountain.voucherApp.shared.constants.ErrorMessage.WRONG_VALUE;
import static com.mountain.voucherApp.shared.constants.ProgramMessage.*;

@Controller
public class VoucherConsoleController {

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherAppUseCase voucherAppUseCase;

    public VoucherConsoleController(InputConsole inputConsole,
                                    OutputConsole outputConsole,
                                    VoucherAppUseCase voucherAppUseCase) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherAppUseCase = voucherAppUseCase;
    }

    public void create() throws IllegalArgumentException {
        outputConsole.choiceDiscountPolicy();
        int policyOrdinal = Integer.valueOf(inputConsole.input());
        DiscountPolicy discountPolicy = DiscountPolicy.find(policyOrdinal);
        if (discountPolicy == null)
            throw new IllegalArgumentException(WRONG_VALUE);
        outputConsole.printMessage(PLEASE_AMOUNT);
        long discountAmount = Long.valueOf(inputConsole.input());
        Voucher voucher = discountPolicy.getVoucher();
        if (voucher.validate(discountAmount)) {
            voucherAppUseCase.create(new VoucherCreateDto(discountPolicy, discountAmount));
        }
    }

    public void showVoucherList() {
        outputConsole.printVoucherList(voucherAppUseCase.showVoucherList());
    }

    public void showCustomerVoucherInfo() {
        outputConsole.printCustomerVoucherInfo(voucherAppUseCase.showCustomerVoucherInfo());
    }

    public void exit() {
        outputConsole.close();
    }

    public void addVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_CUSTOMER_ID);
        UUID customerId = UUID.fromString(inputConsole.input());
        outputConsole.printMessage(PLEASE_INPUT_VOUCHER_ID);
        UUID voucherId = UUID.fromString(inputConsole.input());
        voucherAppUseCase.addVoucher(new VoucherIdUpdateDto(customerId, voucherId));
    }

    public void removeVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_CUSTOMER_ID);
        UUID customerId = UUID.fromString(inputConsole.input());
        voucherAppUseCase.removeVoucher(customerId);
    }

    public void showByVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_VOUCHER_ID);
        UUID voucherId = UUID.fromString(inputConsole.input());
        outputConsole.printCustomerVoucherInfo(voucherAppUseCase.showByVoucher(voucherId));
    }
}
