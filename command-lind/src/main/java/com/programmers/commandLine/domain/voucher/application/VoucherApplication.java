package com.programmers.commandLine.domain.voucher.application;

import com.programmers.commandLine.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.commandLine.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.entity.Menu;
import com.programmers.commandLine.global.entity.Power;
import com.programmers.commandLine.global.entity.VoucherMenu;
import com.programmers.commandLine.global.io.Console;
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

    VoucherApplication(Power power, Console console) {
        this.power = power;
        this.console = console;
    }
    public void run() {
        power.powerOn();
        while (power.isPower()) {
            console.printMenu();

            String selectMenu123 = console.read();
            Menu menu = Menu.selectMenu(selectMenu123);

            String answer = execute(menu);
            console.print(answer);
        }
    }

    private String execute(Menu menu) {
        return switch (menu) {
            case EXIT -> exit();
            case CREATE -> create();
            case LIST -> list();
            case ERROR -> "잘못된 입력 입니다.";
        };
    }

    private String exit() {
        power.powerOff();
        return "프로그램을 종료 합니다.";
    }

    private String create() {
//        console.printVoucher();
//        VoucherMenu voucherMenu = VoucherMenu.selectVoucherMenu(console.read());
//        return switch (voucherMenu) {
//            case FIXEDAMOUNTVOUCHER -> "Create " + voucherRepository
//                    .insert(new FixedAmountVoucher(UUID.randomUUID(), 10L)).getVoucherId().toString();
//            case PERCENTDISCOUNTVOUCHER -> "Create " + voucherRepository
//                    .insert(new PercentDiscountVoucher(UUID.randomUUID(), 10L)).getVoucherId().toString();
//            case ERROR -> "잘못된 입력 입니다.";
//        };
        return "";
    }

    private String list() {
        return "11";
    }

}
