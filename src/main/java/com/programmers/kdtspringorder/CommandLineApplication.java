package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.io.console.Console;
import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CommandLineApplication {

    private static final Input input = new Console();
    private static final Output output = new Console();

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService service = context.getBean(VoucherService.class);

        output.printMessage("=== Voucher Program ===");

        manageVoucherProcess(service);
    }

    private static void manageVoucherProcess(VoucherService service) {
        while (true) {
            output.printMessage("Type exit to exit the program");
            output.printMessage("Type create to create a new voucher");
            output.printMessage("Type list to list all vouchers");

            String inputText = input.inputText();

            if ("exit".equals(inputText)) break;

            switch (inputText) {
                case "create":
                    String type = input.inputText("2000원 쿠폰 생성은 'fixed', 10% 쿠폰 생성은 'percent'를 선택해주세요");
                    if (isWrongType(type)) {
                        output.printMessage("잘못 입력 하셨습니다");
                        break;
                    }
                    Voucher voucher = createVoucher(service, type);
                    output.printMessage("쿠폰 생성에 성공하였습니다");
                    output.print(voucher);
                    break;
                case "list":
                    showList(service);
                    break;
                default:
                    output.printMessage("잘못 입력 하셨습니다. 입력 가능한 명령어는 exit, create, list 입니다.");
            }

            output.newLine();
        }
    }

    private static Voucher createVoucher(VoucherService service, String type) {
        return service.createVoucher(type);
    }

    private static boolean isWrongType(String type) {
        if (type == null) return false;
        return !("fixed".equals(type) || "percent".equals(type));
    }

    private static void showList(VoucherService service) {
        List<Voucher> all = service.findAll();
        for (Voucher voucher : all) {
            output.print(voucher);
        }
    }
}
