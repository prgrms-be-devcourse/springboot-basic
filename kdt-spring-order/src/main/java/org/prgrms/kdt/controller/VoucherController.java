package org.prgrms.kdt.controller;

import org.prgrms.kdt.configure.AppConfiguration;
import org.prgrms.kdt.configure.Voucher;
import org.prgrms.kdt.entity.InputType;
import org.prgrms.kdt.io.IoConsole;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

public class VoucherController {
    private static InputType inputType;

    public static void run(AnnotationConfigApplicationContext applicationContext) {
        var voucherService = applicationContext.getBean(VoucherService.class);
        var ioConsole = applicationContext.getBean(IoConsole.class);

        while (true) {
            //init message 출력
            ioConsole.init();
            String s = ioConsole.input();
            if (inputType.valueOf(s.toUpperCase()) == inputType.CREATE) {
                //바우쳐 선책
                String val = ioConsole.inputText("생성하고자하는 Voucher를 선택해주세요 : [1] Fixed, [2] Percent");
                switch (val) {
                    case "1":
                        // fixed 생성
                        ioConsole.message("FixedAmountVoucher를 생성합니다.");
                        String discount = ioConsole.inputText("할인하고자 하는 금액을 입력해 주세요");
                        voucherService.createVoucher(UUID.randomUUID(), Long.parseLong(discount), 1);
                        ioConsole.message("fixed 바우처 생성이 완료되었습니다.");
                        break;
                    case "2":
                        // percent 생성
                        ioConsole.message("PercentAmountVoucher를 생성합니다.");
                        String percent = ioConsole.inputText("할인하고자 하는 %를 입력해 주세요");
                        voucherService.createVoucher(UUID.randomUUID(), Long.parseLong(percent), 2);
                        ioConsole.message("percent 바우처 생성이 완료되었습니다.");
                        break;
                    default:
                        //다시 입력
                        ioConsole.message("다시 입력 하세요~");
                        break;
                }
            } else if (inputType.valueOf(s.toUpperCase()) == inputType.LIST) {
                List<Voucher> list = voucherService.findAll();
                ioConsole.message("지금까지 생성한 바우처를 출력합니다.");
                ioConsole.outputList();
            } else if (inputType.valueOf(s.toUpperCase()) == inputType.EXIT) {
                ioConsole.exit();
                return;
            } else {
                ioConsole.inputError();
                continue;
            }
            System.out.println();
        }

    }

}
