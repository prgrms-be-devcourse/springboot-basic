package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.VoucherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CommandLineApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run(String... args) throws Exception {
        output.printMessage("=== Voucher Program ===");
        manageVoucherProcess();
    }

    private void manageVoucherProcess() {
        while (true) {
            printCommand();

            String inputText = input.inputText();

            if ("exit".equals(inputText)) break;

            switch (inputText) {
                case "create":
                    String type = input.inputText("2000원 쿠폰 생성은 'FIXED', 10% 쿠폰 생성은 'PERCENT'를 선택해주세요");
                    if (isWrongType(type)) {
                        output.printMessage("잘못 입력 하셨습니다");
                        break;
                    }
                    Voucher voucher = createVoucher(type);
                    output.printMessage("쿠폰 생성에 성공하였습니다");
                    output.print(voucher);
                    break;
                case "list":
                    showList();
                    break;
                default:
                    output.printMessage("잘못 입력 하셨습니다. 입력 가능한 명령어는 exit, create, list 입니다.");
            }
            output.newLine();
        }
    }

    private void printCommand() {
        output.printMessage("Type exit to exit the program");
        output.printMessage("Type create to create a new voucher");
        output.printMessage("Type list to list all vouchers");
    }

    private Voucher createVoucher(String type) {
        return voucherService.createVoucher(type);
    }

    private boolean isWrongType(String type) {
        if (type == null) return false;
        return !("FIXED".equalsIgnoreCase(type) || "PERCENT".equalsIgnoreCase(type));
    }

    private void showList() {
        List<Voucher> all = voucherService.findAll();
        for (Voucher voucher : all) {
            output.print(voucher);
        }
    }

}
