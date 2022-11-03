package com.programmers.commandLine;

import com.programmers.commandLine.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.commandLine.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.entity.VoucherMenu;
import com.programmers.commandLine.global.entity.Menu;
import com.programmers.commandLine.global.io.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

/**
 *
 * VoucherTester 설명을 여기에 작성한다.
 * 바우처를 관리하는 클래스 입니다.
 *
 * @author 장주영
 * @version 1.0.0
 * 작성일 2022/11/02
 *
 **/

public class VoucherTester {
//    private boolean POWER = true;
//    Console console = new Console();
//
//    public void run() {
//        while (POWER) {
//            console.printMenu();
//            Menu menu = Menu.selectMenu(console.read());
//            execution(menu);
//        }
//    }
//
//    private void execution(Menu menu) {
//        String answer = switch (menu) {
//            case EXIT -> exit();
//            case CREATE -> create();
//            case LIST -> list();
//            case ERROR -> "잘못된 입력 입니다.";
//        };
//        console.print(answer);
//    }
//
//    private String exit() {
//        POWER = false;
//        return "프로그램을 종료 합니다.";
//    }
//
//    private String create() {
//
//        console.printVoucher();
//        VoucherMenu voucherMenu = VoucherMenu.selectVoucherMenu(console.read());
//        String answer = switch (voucherMenu) {
//            // 1입력 시
//            case FIXEDAMOUNTVOUCHER -> "Create " + voucherRepository.insert(
//                    new FixedAmountVoucher(UUID.randomUUID(), 10L)).getVoucherId().toString();
//            // 2입력 시
//            case PERCENTDISCOUNTVOUCHER -> "Create " + voucherRepository.insert(
//                    new PercentDiscountVoucher(UUID.randomUUID(), 10L)).getVoucherId().toString();
//            case ERROR -> "잘못된 입력 입니다.";
//        };
//
//        return answer;
//    }
//
//    private String list() {
//        return "";
//    }
}
