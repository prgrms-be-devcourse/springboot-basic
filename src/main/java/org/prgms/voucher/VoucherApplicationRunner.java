package org.prgms.voucher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.voucher.option.Option;
import org.prgms.voucher.service.VoucherService;
import org.prgms.voucher.view.VoucherView;
import org.prgms.voucher.voucher.VoucherPolicy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoucherApplicationRunner implements CommandLineRunner {

    private final VoucherView voucherView;
    private final VoucherService voucherService;
    private final boolean isRunning = true;

    @Override
    public void run(String... args) {

        while (isRunning) {

            voucherView.printOptions();

            try {
                Option option = Option.find(voucherView.readChoice());
                log.info("option input: {}", option);
                switch (option) {
                    case CREATE:
                        createVoucher();
                        break;
                    case LIST:
                        voucherView.printVouchers(voucherService.getVoucherList());
                        break;
                    default:
                        return;
                }
            } catch (Exception e) {
                voucherView.printError(e.getMessage());
            }

        }
    }

    private void createVoucher() {

        VoucherPolicy voucherPolicy = VoucherPolicy.find(voucherView.readVoucherType());
        log.info("voucher type input: {}", voucherPolicy);

        long amount = voucherView.readAmount();
        log.debug("voucher amount input: {}", amount);

        voucherService.create(voucherPolicy, amount);
    }
}
