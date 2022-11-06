package com.programmers.springvoucherservice;

import com.programmers.springvoucherservice.domain.voucher.Voucher;
import com.programmers.springvoucherservice.domain.voucher.VoucherList;
import com.programmers.springvoucherservice.service.VoucherService;
import com.programmers.springvoucherservice.view.View;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.programmers.springvoucherservice.domain.voucher.VoucherList.*;
import static com.programmers.springvoucherservice.domain.voucher.VoucherValidator.isValidateValue;
import static com.programmers.springvoucherservice.menu.Menu.*;

public class CommandLineApplication implements Runnable {
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
            view.showMenu();
            userCommand = view.getUserCommand().toUpperCase();

            try {
                executeUserCommand(userCommand);

            } catch (RuntimeException e) {
                view.printMessage(e.getMessage());
            }
        }
    }

    private void executeUserCommand(String userCommand) {
        if (userCommand.equals(EXIT.getMenu())) {
            return;
        }

        else if (userCommand.equals(CREATE.getMenu())) {
            view.printMessage("고정할인 바우처는 F, 정률 할인 바우처는 P를 입력해주세요.");
            String voucherTypeInput = view.getUserCommand();

            if (!isValidateVoucherType(voucherTypeInput)) {
                throw new RuntimeException("타입을 잘못 입력하셨습니다.");
            }

            String value = getVoucherValue(voucherTypeInput);

            if (!isValidateValue(voucherTypeInput, value)) {
                throw new RuntimeException("바우처 금액을 잘못 입력하셨습니다.");
            }

            createVoucher(voucherTypeInput, value);
            view.printMessage("바우처 생성 성공");
        }

        else if (userCommand.equals(LIST.getMenu())) {
            List<Voucher> vouchers = voucherService.findAll();
            showVoucherList(vouchers);
        }

        else{
            throw new RuntimeException("잘못 입력하셨습니다.");
        }
    }
    private void createVoucher(String voucherTypeInput, String value) {
        VoucherList voucherList = findVoucherList(voucherTypeInput);
        Voucher voucher = VoucherFactory.createVoucher(voucherList, Long.parseLong(value));
        voucherService.register(voucher);
    }

    private String getVoucherValue(String voucherTypeInput) {
        if (voucherTypeInput.equals(FixedAmount.getType())) {
            view.printMessage("바우처의 금액을 입력해주세요.");
            return view.getUserCommand();
        }
        if (voucherTypeInput.equals(PercentDiscount.getType())) {
            view.printMessage("바우처의 할인율을 입력해주세요.");
            return view.getUserCommand();
        }

        throw new RuntimeException("타입을 잘못 입력하셨습니다.");
    }

    private void showVoucherList(List<Voucher> vouchers) {
        for (Voucher voucher : vouchers) {
            view.printVoucher(voucher);
        }
    }
}
