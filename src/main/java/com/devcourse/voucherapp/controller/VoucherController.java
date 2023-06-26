package com.devcourse.voucherapp.controller;

import static com.devcourse.voucherapp.entity.Menu.getMenu;
import static com.devcourse.voucherapp.entity.VoucherType.getVoucherType;

import com.devcourse.voucherapp.entity.Menu;
import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.service.VoucherService;
import com.devcourse.voucherapp.view.InputView;
import com.devcourse.voucherapp.view.OutputView;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherController implements CommandLineRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherService voucherService;

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

            return;
        }

        if (menu.isList()) {
            Collection<Voucher> vouchers = voucherService.list();
            outputView.showAllVouchers(vouchers);
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
