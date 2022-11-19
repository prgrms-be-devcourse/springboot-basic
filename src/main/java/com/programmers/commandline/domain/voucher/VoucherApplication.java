package com.programmers.commandline.domain.voucher;

import com.programmers.commandline.domain.consumer.service.ConsumerService;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.service.VoucherService;
import com.programmers.commandline.global.aop.LogAspect;
import com.programmers.commandline.global.entity.Menu;
import com.programmers.commandline.global.entity.Power;
import com.programmers.commandline.global.io.Console;
import com.programmers.commandline.global.io.Message;
import com.programmers.commandline.global.util.Verification;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {
    private final Power power = new Power();
    private final Console console;
    private final VoucherService voucherService;
    private final ConsumerService consumerService;

    VoucherApplication(Console console, VoucherService voucherService, ConsumerService consumerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.consumerService = consumerService;
    }

    public void run() {
        power.powerOn(); // 전원을 켜기
        while (power.isPower()) {
            try {
                console.print(Message.SELECT_MENU.getMessage());
                String input = console.read();
                Menu menu = Menu.ofMenu(input);
                execute(menu);
            } catch (RuntimeException e) {
                LogAspect.getLogger().error(e.getMessage());
            }
        }
    }

    private void execute(Menu menu) {
        switch (menu) {
            case EXIT -> exit();
            case VOUCHER_CREATE -> create();
            case VOUCHER_LIST -> findVouchers();
            case BLACK_CONSUMER_LIST -> findBlackConsumers();
            case ERROR -> error();
        };
    }

    private void exit() {
        power.powerOff();
        console.print(Message.EXIT.getMessage());
    }

    private void create() {
        console.print(Message.SELECT_VOUCHER.getMessage());

        final String input = console.read();
        VoucherType voucherType = VoucherType.ofNumber(input);

        console.print(voucherType.getMessage());

        String discount = console.read();
        Verification.validateParseToNumber(discount);
        String uuid = voucherService.create(voucherType, Long.parseLong(discount)).toString();

        console.print(uuid);
    }

    private void error() {
        console.print(Message.MENU_ERROR.getMessage());
    }

    private void findBlackConsumers() {
        console.print(consumerService.findAll());
    }

    private void findVouchers() {
        console.print(voucherService.findVouchers());
    }
}
