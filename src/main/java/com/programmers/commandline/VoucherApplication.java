package com.programmers.commandline;

import com.programmers.commandline.domain.consumer.service.ConsumerService;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.service.VoucherService;
import com.programmers.commandline.global.aop.LogAspect;
import com.programmers.commandline.global.entity.Menu;
import com.programmers.commandline.global.entity.Power;
import com.programmers.commandline.global.io.Console;
import com.programmers.commandline.global.io.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
        power.powerOn();
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
            case BLACK_CONSUMER_LIST -> findConsumers();
            case CONSUMER -> insertConsumer();
        }
    }

    private void insertConsumer() {
        console.print(Message.INSERT_CONSUMER_NAME.getMessage());
        String name = console.read();
        console.print(Message.INSERT_CONSUMER_EMAIL.getMessage());
        String email = console.read();

        String consumerId = consumerService.insert(UUID.randomUUID(), name, email);
        console.print(consumerId + "\n");
    }

    private void exit() {
        power.powerOff();
        console.print(Message.EXIT.getMessage());
    }

    private void create() {
        console.print(Message.SELECT_VOUCHER.getMessage());

        final String input = console.readNumber();
        int code = Integer.parseInt(input);
        VoucherType voucherType = VoucherType.ofNumber(code);

        console.print(voucherType.getMessage());

        String discount = console.readNumber();
        String uuid = voucherService.insert(voucherType, Long.parseLong(discount));

        console.print(uuid);
    }

    private void findConsumers() {
        String consumers = consumerService.findAll();
        console.print(consumers);
    }

    private void findVouchers() {
        String vouchers = voucherService.findAll();
        console.print(vouchers);
    }


}
