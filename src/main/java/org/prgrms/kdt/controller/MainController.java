package org.prgrms.kdt.controller;

import org.prgrms.kdt.service.customer.CustomerService;
import org.prgrms.kdt.utils.Option;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.utils.VoucherType;
import org.prgrms.kdt.io.Console;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MainController {
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private Console console;
    private VoucherService voucherService;
    private CustomerService customerService;

    public MainController(Console console, VoucherService voucherService, CustomerService customerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run() {
        boolean isRunnable = true;

        while (isRunnable) {
            try {
                console.menu();
                String input = console.input("원하시는 명령어를 입력해주세요 : ").toUpperCase();

                Option option = Option.of(input);
                switch (option) {
                    case EXIT -> isRunnable = false;
                    case CREATE -> create();
                    case LIST -> showList();
                    case BLACKLIST -> showBlackList();
                }
            } catch (RuntimeException e) {
                console.printMessage(e.getMessage());
            }
        }
    }

    private void showBlackList() {
        List<List<String>> all = customerService.getBlackListCustomers();

        if (all.isEmpty()) {
            console.printMessage("조회 결과 기록이 존재하지 않습니다.");
        }
        console.printAllBlackList(all);
    }

    private void showList() {
        List<Voucher> all = voucherService.getVouchers();

        if (all.isEmpty()) {
            console.printMessage("조회 결과 기록이 존재하지 않습니다.");
        }
        console.printAll(all);
    }

    private void create() {
        String input = console.input("원하시는 Voucher 입력해주세요 : (1) FixedAmountVoucher (2)PercentDiscountVoucher");
        VoucherType voucherType = VoucherType.of(input);
        String discountAmount = console.input("원하시는 할인 금액 또는 퍼센트를 입력해주세요");
        voucherService.save(voucherType, Long.valueOf(discountAmount));
    }

}
