package com.programmers.voucher;

import com.programmers.voucher.menu.Menu;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.View;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.programmers.voucher.menu.Menu.EXIT;
import static com.programmers.voucher.menu.Menu.findMenu;
import static com.programmers.voucher.menu.Message.*;
import static com.programmers.voucher.voucher.VoucherList.findVoucherList;
import static com.programmers.voucher.voucher.VoucherList.isValidateVoucherType;
import static com.programmers.voucher.voucher.VoucherValidator.isValidateValue;

public class CommandLineApplication implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private final View view;
    private final VoucherService voucherService;

    @Autowired
    public CommandLineApplication(View view, VoucherService voucherService) {
        this.view = view;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        String userCommand = "";
        while (!userCommand.equals(EXIT.getMenu())) {
            view.printMessage(GREETING_MESSAGE.getMessage());
            userCommand = view.getUserCommand().toUpperCase();

            try {
                Menu userMenu = findMenu(userCommand);
                executeUserCommand(userMenu);
            } catch (RuntimeException e) {
                logger.error("사용자 커맨드 입력값 오류", e);
                view.printMessage(e.getMessage());
            }
        }
    }

    private void executeUserCommand(Menu userMenu) {
        switch (userMenu) {
            case CREATE:
                createVoucher();
                break;

            case LIST:
                showVoucherList();
                break;

            case EXIT:
                return;
        }
    }

    private void createVoucher() {
        view.printMessage(VOUCHER_TYPE_MESSAGE.getMessage());

        String voucherTypeInput = getVoucherTypeInput();
        String value = getVoucherValue(voucherTypeInput);

        VoucherList voucherList = findVoucherList(voucherTypeInput);
        Voucher voucher = VoucherFactory.createVoucher(voucherList, Long.parseLong(value));
        voucherService.register(voucher);

        view.printMessage(VOUCHER_CREATE_SUCCESS.getMessage());
    }

    private String getVoucherTypeInput() {
        String voucherTypeInput = view.getUserCommand();

        if (!isValidateVoucherType(voucherTypeInput)) {
            throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
        }
        return voucherTypeInput;
    }


    private String getVoucherValue(String type) {
        view.printMessage(VOUCHER_VALUE_MESSAGE.getMessage());
        String value = view.getUserCommand();

        if (!isValidateValue(type, value)) {
            throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
        }

        return value;
    }

    private void showVoucherList() {
        List<Voucher> vouchers = voucherService.findAll();
        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }
}
