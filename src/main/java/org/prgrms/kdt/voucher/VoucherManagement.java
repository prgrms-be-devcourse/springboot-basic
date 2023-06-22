package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.utils.Option;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.utils.VoucherType;
import org.prgrms.kdt.voucher.io.Console;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class VoucherManagement {
    private Console console;
    private VoucherService voucherService;

    public VoucherManagement(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        boolean isRunnable = true;

        while (isRunnable) {
            try {
                console.menu();
                String input = console.input("원하시는 명령어를 입력해주세요 : ").toUpperCase();

                Option option = Option.valueOf(input);
                switch (option) {
                    case EXIT -> isRunnable = false;
                    case CREATE -> create();
                    case LIST -> list();
                    default -> {
                        isRunnable = false;
                    }
                }
            } catch (RuntimeException | IOException | ClassNotFoundException e) {
                console.printMessage(e.getMessage());
            }
        }
    }

    private void list() throws IOException, ClassNotFoundException {
        List<Voucher> all = voucherService.getVouchers();

        if (all.isEmpty()) {
            console.printMessage("조회 결과 기록이 존재하지 않습니다.");
        }
        console.printAll(all);
    }

    private void create() throws IOException {
        String input = console.input("원하시는 Voucher 입력해주세요 : (1) FixedAmountVoucher (2)PercentDiscountVoucher");
        VoucherType voucherType = VoucherType.of(input);
        String discountAmount = console.input("원하시는 할인 금액 또는 퍼센트를 입력해주세요");
        voucherService.save(voucherType, Long.valueOf(discountAmount));
    }

}
