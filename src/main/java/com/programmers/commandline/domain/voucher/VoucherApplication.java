package com.programmers.commandline.domain.voucher;

import com.programmers.commandline.domain.consumer.service.CousumerService;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.service.VoucherService;
import com.programmers.commandline.global.entity.Menu;
import com.programmers.commandline.global.entity.Power;
import com.programmers.commandline.global.factory.LoggerFactory;
import com.programmers.commandline.global.io.Console;
import com.programmers.commandline.global.io.Message;
import com.programmers.commandline.global.util.Verification;
import org.springframework.stereotype.Component;

@Component
public class VoucherApplication {
    private final Power power = new Power();
    private final Console console;
    private final VoucherService voucherService;
    private final CousumerService cousumerService;

    VoucherApplication(Console console, VoucherService voucherService, CousumerService cousumerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.cousumerService = cousumerService;
    }

    public void run() {

        LoggerFactory.getLogger().info("VoucherApplication run 실행");
        power.powerOn(); // 전원을 켜기
        while (power.isPower()) {
            console.print(Message.SELECT_MENU.getMessage());
            String input = console.read();
            Menu menu = Menu.ofMenu(input);
            String answer = execute(menu);
            console.print(answer);
        }
    }

    private String execute(Menu menu) {
        LoggerFactory.getLogger().info("VoucherApplication execute 실행");
        return switch (menu) {
            case EXIT -> exit();
            case VOUCHER_CREATE -> voucherCreate();
            case VOUCHER_LIST -> voucherList();
            case BLACK_CONSUMER_LIST -> blackConsumerList();
            case ERROR -> error();
        };
    }

    private String exit() {
        LoggerFactory.getLogger().info("VoucherApplication exit 실행");
        power.powerOff();
        return Message.EXIT.getMessage();
    }

    private String voucherCreate() {
        LoggerFactory.getLogger().info("VoucherApplication voucherCreate 실행");

        console.print(Message.SELECT_VOUCHER.getMessage());

        final String input = console.read();

        try {
            VoucherType voucherType = VoucherType.ofNumber(input);

            console.print(voucherType.getMessage());

            String discount = console.read();
            Verification.validateParseToNumber(discount);

            return voucherService.create(voucherType, Long.parseLong(discount)).toString();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    private String error() {
        LoggerFactory.getLogger().error("Menu toCode 에러 발생");

        return Message.MENU_ERROR.getMessage();
    }

    private String blackConsumerList() {
        LoggerFactory.getLogger().info("VoucherApplication blackConsumerList 실행");

        try {
            return cousumerService.blackCousumerList();
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private String voucherList() {
        LoggerFactory.getLogger().info("VoucherApplication voucherList 실행");
        try {
            return voucherService.list() + "\n\n";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
