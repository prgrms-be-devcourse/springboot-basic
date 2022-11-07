package com.programmers.voucher;

import com.programmers.voucher.menu.Message;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.view.View;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.programmers.voucher.menu.Menu.*;
import static com.programmers.voucher.menu.Message.*;
import static com.programmers.voucher.voucher.VoucherList.*;
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
                executeUserCommand(userCommand);
            } catch (RuntimeException e) {
                logger.error("사용자 커맨드 입력값 오류", e);
                view.printMessage(e.getMessage());
            }
        }
    }

    private void executeUserCommand(String userCommand) {
        if (userCommand.equals(EXIT.getMenu())) {
            return;
        } else if (userCommand.equals(CREATE.getMenu())) {
            view.printMessage(VOUCHER_TYPE_MESSAGE.getMessage());
            String voucherTypeInput = view.getUserCommand();

            if (!isValidateVoucherType(voucherTypeInput)) {
                throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
            }

            String value = getVoucherValue();

            if (!isValidateValue(voucherTypeInput, value)) {
                throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
            }

            createVoucher(voucherTypeInput, value);
            view.printMessage(VOUCHER_CREATE_SUCCESS.getMessage());

        } else if (userCommand.equals(LIST.getMenu())) {
            List<Voucher> vouchers = voucherService.findAll();
            showVoucherList(vouchers);
        } else {
            throw new RuntimeException(INPUT_ERROR_MESSAGE.getMessage());
        }
    }

    private void createVoucher(String voucherTypeInput, String value) {
        VoucherList voucherList = findVoucherList(voucherTypeInput);
        Voucher voucher = VoucherFactory.createVoucher(voucherList, Long.parseLong(value));
        voucherService.register(voucher);
    }

    private String getVoucherValue() {
        view.printMessage(VOUCHER_VALUE_MESSAGE.getMessage());
        return view.getUserCommand();
    }


    private void showVoucherList(List<Voucher> vouchers) {
        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }
}
