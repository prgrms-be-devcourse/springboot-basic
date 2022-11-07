package com.programmers.commandLine.domain.voucher.application;

import com.programmers.commandLine.domain.consumer.service.ConsumerService;
import com.programmers.commandLine.domain.voucher.entity.VoucherMenu;
import com.programmers.commandLine.domain.voucher.service.VoucherService;
import com.programmers.commandLine.global.entity.Menu;
import com.programmers.commandLine.global.entity.Power;
import com.programmers.commandLine.global.factory.LoggerFactory;
import com.programmers.commandLine.global.io.Console;
import com.programmers.commandLine.global.io.Message;
import org.springframework.stereotype.Component;


/**
 * VoucherApplication의 설명을 여기에 작성한다.
 * 바우처 어플리케이션을 구동 시키는 클래스 입니다.
 *
 * @author 장주영
 * @version 1.0.0
 * 작성일 2022/11/03
 **/
@Component
public class VoucherApplication {

    private final Power power;
    private final Console console;
    private final VoucherService voucherService;
    private final ConsumerService consumerService;

    VoucherApplication(Power power, Console console, VoucherService voucherService,ConsumerService consumerService) {
        this.power = power;
        this.console = console;
        this.voucherService = voucherService;
        this.consumerService = consumerService;
    }

    public void run() {
        LoggerFactory.getLogger().info("VoucherApplication run 실행");
        power.powerOn(); // 전원을 켜기
        while (power.isPower()) {
            console.print(Message.SELECT_MENU.getMessage()); // 메뉴가 무엇이 있는지 출력
            String input = console.read(); // 메뉴를 입력 받는다.
            Menu menu = Menu.selectMenu(input); // Menu enum을 출력한다.
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

    private String blackConsumerList() {
        LoggerFactory.getLogger().info("VoucherApplication blackConsumerList 실행");
        try {
            return consumerService.blackList() + "\n\n";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
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
        VoucherMenu voucherMenu = VoucherMenu.selectVoucherMenu(input);

        switch (voucherMenu) {
            case FIXEDAMOUNTVOUCHER -> console.print(Message.DISCOUNT_AMOUNT.getMessage());
            case PERCENTDISCOUNTVOUCHER -> console.print(Message.DISCOUNT_RATE.getMessage());
            case ERROR -> console.print(Message.VOUCHER_MENU_ERROR.getMessage());
        }

        String discount = console.read();

        try {
            return voucherService.create(voucherMenu, discount).toString() + "\n\n";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    private String voucherList() {
        LoggerFactory.getLogger().info("VoucherApplication voucherList 실행");
        try {
            return voucherService.list() + "\n\n";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    private String error(){
        LoggerFactory.getLogger().error("Menu toCode 에러 발생");
        return Message.MENU_ERROR.getMessage();
    }

}



