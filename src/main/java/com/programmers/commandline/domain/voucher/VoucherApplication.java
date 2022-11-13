package com.programmers.commandline.domain.voucher;

import com.programmers.commandline.domain.consumer.service.ConsumerService;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.service.VoucherService;
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

                String input = console.read(); // 여기서 에러 발생

                Menu menu = Menu.ofMenu(input);

                String answer = execute(menu);

                console.print(answer);
            } catch (RuntimeException e) {
                console.print(e.getMessage());
            }

        }
    }

    private String execute(Menu menu) {
        return switch (menu) {
            case EXIT -> exit();
            case VOUCHER_CREATE -> create();
            case VOUCHER_LIST -> vouchers();
            case BLACK_CONSUMER_LIST -> blackConsumers();
            case ERROR -> error();
        };
    }

    private String exit() {
        power.powerOff();
        return Message.EXIT.getMessage();
    }

    private String create() {
        console.print(Message.SELECT_VOUCHER.getMessage());

        final String input = console.read();

        try {
            VoucherType voucherType = VoucherType.ofNumber(input);

            console.print(voucherType.getMessage());

            String discount = console.read();

            Verification.validateParseToNumber(discount);

            return voucherService.create(voucherType, Long.parseLong(discount)).toString();
        } catch (RuntimeException e) {
            throw new RuntimeException("create에서 에러가 발생했습니다.");
        }
    }

    private String error() {
        return Message.MENU_ERROR.getMessage();
    }

    private String blackConsumers() {
        try {
            return consumerService.findAll();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String vouchers() {
        try {
            return voucherService.list();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
