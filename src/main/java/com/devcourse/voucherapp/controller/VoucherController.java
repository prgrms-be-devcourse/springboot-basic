package com.devcourse.voucherapp.controller;

import static com.devcourse.voucherapp.entity.Menu.getMenu;
import static com.devcourse.voucherapp.entity.VoucherType.getVoucherType;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.service.VoucherService;
import com.devcourse.voucherapp.view.InputView;
import com.devcourse.voucherapp.view.OutputView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherService voucherService;

    @Autowired
    public VoucherController(InputView inputView, OutputView outputView,
        VoucherService voucherService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) {
        while (true) {
            outputView.showMenu();
            String menuNumber = inputView.inputWithTrimming();
            Menu menu = getMenu(menuNumber);

            if (menu.isQuit()) {
                break;
            }

            execute(menu);
        }

        outputView.showQuitMessage();
    }

    private void execute(Menu menu) {
        if (menu.isCreate()) {
            outputView.showVoucherTypes();

            String voucherTypeNumber = inputView.inputWithTrimming();
            VoucherType voucherType = getVoucherType(voucherTypeNumber);

            showDiscountAmountInputMessage(voucherType);
            String discountAmount = inputView.inputWithTrimming();

            Voucher voucher = voucherService.create(voucherType, discountAmount);
            outputView.showVoucherCreationSuccessMessage(voucher.toString());
        }
    }

    private void showDiscountAmountInputMessage(VoucherType voucherType) {
        if (voucherType.isFix()) {
            outputView.showFixDiscountPriceInputMessage();

            return;
        }

        if (voucherType.isPercent()) {
            outputView.showPercentDiscountRateInputMessage();
        }
    }
}
