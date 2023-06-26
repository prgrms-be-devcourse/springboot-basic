package org.prgms.voucher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgms.voucher.option.Option;
import org.prgms.voucher.service.VoucherService;
import org.prgms.voucher.view.VoucherView;
import org.prgms.voucher.voucher.Voucher;
import org.prgms.voucher.voucher.VoucherFactory;
import org.prgms.voucher.voucher.VoucherPolicy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController implements CommandLineRunner {

    private final VoucherView voucherView;
    private final VoucherService voucherService;
    private final VoucherFactory voucherFactory;
    private final boolean isRunning = true;

    @Override
    public void run(String... args) {

        while (isRunning) {

            voucherView.printOptions();

            try {
                log.info("Voucher Controller: read option input");
                Option option = Option.find(voucherView.readChoice());
                log.debug("option input: {}", option);
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
                log.error("message: {}", e.getMessage());
                voucherView.printError(e.getMessage());
            }

        }
    }

    private void createVoucher() {

        log.info("Voucher Controller: read voucher type input");
        VoucherPolicy voucherPolicy = VoucherPolicy.find(voucherView.readVoucherType());
        log.debug("voucher type input: {}", voucherPolicy);
        log.info("Voucher Controller: read voucher amount input");
        Voucher voucher = voucherFactory.createVoucher(voucherPolicy, voucherView.readAmount());
        log.debug("voucher amount input: {}", voucher);

        voucherService.save(voucher);

        log.info("Voucher Controller: Voucher 생성: {}", voucher);
    }
}
