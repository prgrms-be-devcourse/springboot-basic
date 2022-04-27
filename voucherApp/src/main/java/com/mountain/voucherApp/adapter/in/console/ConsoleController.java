package com.mountain.voucherApp.adapter.in.console;

import com.mountain.voucherApp.adapter.in.VoucherAppController;
import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.port.in.VoucherCreateDto;
import com.mountain.voucherApp.application.port.in.VoucherIdUpdateDto;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import com.mountain.voucherApp.shared.io.InputConsole;
import com.mountain.voucherApp.shared.io.OutputConsole;
import com.mountain.voucherApp.shared.utils.DiscountPolicyUtil;
import com.mountain.voucherApp.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.mountain.voucherApp.shared.constants.ProgramMessage.*;

@Controller
public class ConsoleController implements VoucherAppController {

    private static final Logger log = LoggerFactory.getLogger(ConsoleController.class);
    private static final int NOT_EXIST_DATA_NUMBER = -1;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherAppUseCase voucherAppUseCase;

    public ConsoleController(InputConsole inputConsole,
                             OutputConsole outputConsole,
                             VoucherAppUseCase voucherAppUseCase) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherAppUseCase = voucherAppUseCase;
    }

    private boolean choiceNumberValidate(int choiceNumber, int maxSize) {
        if (choiceNumber == NOT_EXIST_DATA_NUMBER)
            return false;
        if (choiceNumber > maxSize) {
            outputConsole.printMessage(WRONG_INPUT);
            return false;
        }
        return true;
    }
    @Override
    public void create() throws NumberFormatException {
        outputConsole.choiceDiscountPolicy();
        int policyId = Integer.valueOf(inputConsole.input());
        if (choiceNumberValidate(policyId, DiscountPolicy.values().length)) {
            outputConsole.printMessage(PLEASE_AMOUNT);
            long discountAmount = Long.valueOf(inputConsole.input());
            Voucher voucher = DiscountPolicyUtil.getVoucher(policyId);
            if (voucher.validate(discountAmount)) {
                voucherAppUseCase.create(new VoucherCreateDto(policyId, discountAmount));
            }
        }
    }
    @Override
    public void showVoucherList() {
        outputConsole.printVoucherList(voucherAppUseCase.showVoucherList());
    }
    @Override
    public void showCustomerVoucherInfo() {
        outputConsole.printCustomerVoucherInfo(voucherAppUseCase.showCustomerVoucherInfo());
    }
    @Override
    public void exit() {
        outputConsole.close();
    }
    @Override
    public void addVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_CUSTOMER_ID);
        UUID customerId = UUID.fromString(inputConsole.input());
        outputConsole.printMessage(PLEASE_INPUT_VOUCHER_ID);
        UUID voucherId = UUID.fromString(inputConsole.input());
        voucherAppUseCase.addVoucher(new VoucherIdUpdateDto(customerId, voucherId));
    }
    @Override
    public void removeVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_CUSTOMER_ID);
        UUID customerId = UUID.fromString(inputConsole.input());
        voucherAppUseCase.removeVoucher(customerId);
    }
    @Override
    public void showByVoucher() {
        outputConsole.printMessage(PLEASE_INPUT_VOUCHER_ID);
        UUID voucherId = UUID.fromString(inputConsole.input());
        outputConsole.printCustomerVoucherInfo(voucherAppUseCase.showByVoucher(voucherId));
    }
}
