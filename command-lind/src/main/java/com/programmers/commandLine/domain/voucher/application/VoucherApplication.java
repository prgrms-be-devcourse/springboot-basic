package com.programmers.commandLine.domain.voucher.application;

import com.programmers.commandLine.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.commandLine.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.service.VoucherService;
import com.programmers.commandLine.global.entity.Menu;
import com.programmers.commandLine.global.entity.Power;
import com.programmers.commandLine.domain.voucher.entity.VoucherMenu;
import com.programmers.commandLine.global.io.Console;
import com.programmers.commandLine.global.io.Message;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * VoucherApplication의 설명을 여기에 작성한다.
 * 바우처 어플리케이션을 구동 시키는 클래스
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

    VoucherApplication(Power power, Console console, VoucherService voucherService) {
        this.power = power;
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {
        power.powerOn();
        while (power.isPower()) {
            console.print(Message.SELECT_MENU.getMessage());

            String input = console.read();
            Menu menu = Menu.selectMenu(input);

            String answer = execute(menu);
            console.print(answer);
        }
    }

    private String execute(Menu menu) {
        return switch (menu) {
            case EXIT -> exit();
            case CREATE -> create();
            case LIST -> list();
            case ERROR -> Message.MENU_ERROR.getMessage();
        };
    }

    private String exit() {
        power.powerOff();
        return Message.EXIT.getMessage();
    }

    private String create() {
        console.print(Message.SELECT_VOUCHER.getMessage());
        final String input = console.read();
        VoucherMenu voucherMenu = VoucherMenu.selectVoucherMenu(input);

        switch (voucherMenu) {
            case FIXEDAMOUNTVOUCHER -> console.print(Message.DISCOUNT_AMOUNT.getMessage());
            case PERCENTDISCOUNTVOUCHER -> console.print(Message.DISCOUNT_RATE.getMessage());
            case ERROR -> console.print(Message.VOUCHER_MENU_ERROR.getMessage());
        };

        String discount = console.read();

        try {
            return voucherService.create(voucherMenu, discount).toString() + "\n\n";
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
    }

    private String list() {
        return voucherService.list() + "\n\n";
    }
}
// 에프 멘토님 추천 도서
// 객체지향 관련 서적
// 오브젝트
// 도메인 주도 개발 시작하기
