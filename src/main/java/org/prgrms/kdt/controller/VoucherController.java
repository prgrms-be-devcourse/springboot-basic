package org.prgrms.kdt.controller;

import java.util.UUID;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.Menu;
import org.prgrms.kdt.view.OutPutView;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private static final int VOUCHER_KEYWORD_INDEX = 0;
    private static final int VOUCHER_DISCOUNT_INDEX = 1;

    private final VoucherRepository voucherRepository;

    public VoucherController(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void run() {
        while (true) {
            OutPutView.showsInitMenu();
            execute(InputView.inputTheMenu());
        }
    }

    private void execute(Menu menu) {
        switch (menu) {
            case EXIT:
                System.exit(0);
            case CREATE:
                selectTheVoucher();
                break;
            case LIST:
                OutPutView.showsVoucherHistory(voucherRepository.getAll());
                break;
            case NONE:
                OutPutView.showsRetryMessage();
                break;
        }
    }

    private void selectTheVoucher() {
        OutPutView.showsVoucherMenu();

        String[] voucherMenu = InputView.inputTheVoucher();
        VoucherType voucherType = VoucherType.findVoucherMenu(voucherMenu[VOUCHER_KEYWORD_INDEX]);
        makeVoucher(voucherType, Long.parseLong(voucherMenu[VOUCHER_DISCOUNT_INDEX]));
    }

    private void makeVoucher(VoucherType voucherMenu, long discountValue) {
        logger.info("Make a Voucher => [Type : {}] [Value : {}]", voucherMenu, discountValue);
        switch (voucherMenu) {
            case FIXED:
                voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), discountValue));
                break;
            case PERCENT:
                voucherRepository.insert(
                    new PercentDiscountVoucher(UUID.randomUUID(), discountValue));
                break;
            case NONE:
                OutPutView.showsRetryMessage();
                break;
        }
    }

}
