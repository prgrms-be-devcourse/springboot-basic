package com.programmers.assignment.voucher.engine.service;

import com.programmers.assignment.voucher.engine.io.ConsoleInput;
import com.programmers.assignment.voucher.engine.io.ConsoleOutput;
import com.programmers.assignment.voucher.engine.repository.VoucherRepository;
import com.programmers.assignment.voucher.util.domain.Menu;
import com.programmers.assignment.voucher.util.domain.VoucherVariable;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final ConsoleInput input;

    private final ConsoleOutput output;

    private final VoucherRepository voucherRepository;

    public MenuService(ConsoleInput input, ConsoleOutput output, VoucherRepository voucherRepository) {
        this.input = input;
        this.output = output;
        this.voucherRepository = voucherRepository;
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

    public String createVoucher() {
        String discountWay = VoucherVariable.chooseDiscountWay(input.selectVoucher(VOUCHER_MESSAGE));
        return discountWay;
    }

    public void exitApplication() {
        output.exitApplication();
        System.exit(0);
    }

    public void showVouchers() {
        var voucherList = voucherRepository.findAll();
        if (voucherList.size() == 0) {
            output.voucherListSizeZero();
            return;
        }
        output.findVoucherList(voucherList);
    }
}
