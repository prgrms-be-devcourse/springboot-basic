package org.prgms.voucher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.voucher.service.VoucherService;
import org.prgms.voucher.view.Option;
import org.prgms.voucher.view.VoucherPolicy;
import org.prgms.voucher.view.VoucherView;
import org.prgms.voucher.voucher.FixedAmountVoucher;
import org.prgms.voucher.voucher.PercentDiscountVoucher;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {

    private final VoucherView voucherView;
    private final VoucherService voucherService;

    @Override
    public void run() {

        while (true) {

            voucherView.printOptions();

            try {
                Option option = Option.find(voucherView.readChoice());

                switch (option) {
                    case CREATE:
                        log.info("create");
                        createVoucher();
                        break;
                    case LIST:
                        log.info("list");
                        voucherView.printVouchers(voucherService.list());
                        break;
                    default:
                        log.info("exit");
                        System.exit(0);
                }
            } catch (Exception e) {
                voucherView.printError(e.getMessage());
            }

        }
    }

    private void createVoucher() {

        VoucherPolicy voucherPolicy = VoucherPolicy.find(voucherView.readVoucherType());
        log.info("create voucher : {}", voucherPolicy);

        switch (voucherPolicy) {
            case FIXED_AMOUNT:
                long amount = voucherView.readAmount();
                log.info("create fixed amount voucher, amount : {}", amount);
                voucherService.create(new FixedAmountVoucher(amount));
                break;
            case PERCENT_DISCOUNT:
                long percentage = voucherView.readPercentage();
                log.info("create percent discount voucher, percentage : {}", percentage);
                voucherService.create(new PercentDiscountVoucher(percentage));
                break;
            default:
                throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

}
