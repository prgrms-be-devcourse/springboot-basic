package org.prgrms.kdt;

import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherManagement {
    private Console console;
    private VoucherService voucherService;

    public VoucherManagement(Console console,VoucherService voucherService) {
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
                    case EXIT-> isRunnable = false;
                    case CREATE -> create();
                    case LIST -> list();
                    default -> {
                        isRunnable = false;
                    }
                }

            } catch (RuntimeException e) {
                console.printMessage(e.getMessage());
            }
        }
    }

    private void list() {
        List<Voucher> all = voucherService.getVouchers();

        if (all.isEmpty()) {
            console.printMessage("조회 결과 기록이 존재하지 않습니다.");
        }
        console.printAll(all);
    }

    private void create() {
        String input = console.input("원하시는 Voucher 입력해주세요 : (1) FixedAmountvoucher (2)PercentDiscountVoucher");
        VoucherType voucherType = VoucherType.of(input);
        String discountAmount = console.input("원하시는 할인 금액 또는 퍼센트를 입력해주세요");
        voucherService.save(voucherType,Long.valueOf(discountAmount));
    }

}
