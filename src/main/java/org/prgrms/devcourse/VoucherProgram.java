package org.prgrms.devcourse;

import org.prgrms.devcourse.domain.*;
import org.prgrms.devcourse.service.BlackUserService;
import org.prgrms.devcourse.service.VoucherService;
import org.prgrms.devcourse.ui.UserInterface;

import java.util.*;

import static org.prgrms.devcourse.domain.VoucherProgramMenu.FIXED_AMOUNT_DISCOUNT_VOUCHER;
import static org.prgrms.devcourse.domain.VoucherProgramMenu.PERCENT_DISCOUNT_VOUCHER;


public class VoucherProgram {
    private VoucherService voucherService;
    private BlackUserService blackUserService;
    private UserInterface userInterface;


    public VoucherProgram(VoucherService voucherService,
                          BlackUserService blackUserService,
                          UserInterface userInterface) {
        this.voucherService = voucherService;
        this.blackUserService = blackUserService;
        this.userInterface = userInterface;
    }

    public void run() {
        String userInput = null;
        while (true) {
            userInterface.showVoucherProgramMenuInterface();
            userInput = userInterface.input();

            switch (VoucherProgramMenu.findByUserInput(userInput)) {
                case CREATE -> {
                    userInterface.showVoucherTypeSelectMessage();
                    userInput = userInterface.input();
                    userInterface.showVoucherDiscountValueInputMessage();
                    createDiscreteVoucher(userInput);
                }
                case LIST -> {
                    List<Voucher> voucherList = voucherService.getVoucherList();
                    userInterface.showVoucherList(voucherList);
                }
                case BLACK_LIST -> {
                    List<BlackUser> blackList = blackUserService.getBlackUserList();
                    userInterface.showBlackList(blackList);
                }
                case EXIT -> {
                    userInterface.showVoucherProgramTerminateMessage();
                    return;
                }
                default -> userInterface.showInvalidInputMessage();
            }
        }
    }

    private void createDiscreteVoucher(String voucherTypeSelect) {
        String discontValue = userInterface.input();
        if (VoucherProgramMenu.findByUserInput(voucherTypeSelect).equals(FIXED_AMOUNT_DISCOUNT_VOUCHER)) {
            voucherService.createVoucher(FixedAmountVoucher.of(UUID.randomUUID(), Long.parseLong(discontValue)));
        } else if (VoucherProgramMenu.findByUserInput(voucherTypeSelect).equals(PERCENT_DISCOUNT_VOUCHER)) {
            voucherService.createVoucher(PercentDiscountVoucher.of(UUID.randomUUID(), Long.parseLong(discontValue)));
        }
    }
}
