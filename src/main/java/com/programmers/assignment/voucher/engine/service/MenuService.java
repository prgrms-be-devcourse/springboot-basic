package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.controller.VoucherController;
import com.programmers.assignment.voucher.engine.io.Input;
import com.programmers.assignment.voucher.engine.io.Output;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.util.domain.Menu;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;

//@Service
public class MenuService {
    private Input input;

    private Output output;

    private VoucherRepository voucherRepository;

    private VoucherController voucherController;

    public MenuService(Input input, Output output, VoucherRepository voucherRepository, VoucherController voucherController) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
        this.voucherController = voucherController;
    }

    private final String MENU_MESSAGE =
            "===Voucher Program===\n" +
                    "Type exit to exit the program.\n" +
                    "Type create to create a new voucher.\n" +
                    "Type list to list all vouchers";

    private final String VOUCHER_MESSAGE =
            "===Select Voucher===\n" +
                    "Type FIXED to create new FixedVoucher.\n" +
                    "Type PERCENT to create new PercentVoucher.";


    public String inputCommand() {
        return Menu.chooseMenu(input.inputCommand(MENU_MESSAGE));
    }

    public void createVoucher() {
        String discountWay = VoucherVariable.chooseDiscountWay(input.selectVoucher(VOUCHER_MESSAGE));
        voucherController.makeVoucher(discountWay);
    }

    public void exitApplication() {
        output.exitApplication();
        System.exit(0);
    }

    public void showVouchers() {
        var map = voucherRepository.findAll();
        output.findVoucherList(map);
    }
}
