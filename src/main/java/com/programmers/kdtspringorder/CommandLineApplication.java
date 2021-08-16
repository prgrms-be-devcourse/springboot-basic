package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.io.Console;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CommandLineApplication {

    public static final int FIXED_VOUCHER = 1;
    public static final int PERCENT_VOUCHER = 2;

    private static final Console console = new Console();

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService service = context.getBean(VoucherService.class);

        console.printMessage("=== Voucher Program ===");

        while (true) {
            console.printMessage("Type exit to exit the program");
            console.printMessage("Type create to create a new voucher");
            console.printMessage("Type list to list all vouchers");

            String input = console.inputText();

            if("exit".equals(input)) break;

            switch (input) {
                case "create": createVoucher(service);
                    break;
                case "list": showList(service);
                    break;
                default: console.printMessage("잘못 입력 하셨습니다. 입력 가능한 명령어는 exit, create, list 입니다.");
            }

            console.newLine();
        }
    }

    private static void createVoucher(VoucherService service) {
        int type = parse(console.inputText("2000원 쿠폰 생성은 1번, 10% 쿠폰 생성은 2번을 선택해주세요"));
        if(type == -1) {
            console.printMessage("잘못 입력 하셨습니다");
            return;
        }

        Voucher voucher = service.createVoucher(type);
        console.printMessage("쿠폰 생성에 성공하였습니다");
        console.print(voucher);
    }

    private static int parse(String amount) {
        if (amount.length() != 1) return -1;

        char c = amount.charAt(0);
        if (Character.isDigit(c)) {
            return c-'0';
        } else {
            return -1;
        }
    }
    private static void showList(VoucherService service) {
        List<Voucher> all = service.findAll();
        for (Voucher voucher : all) {
            console.print(voucher);
        }
    }
}
