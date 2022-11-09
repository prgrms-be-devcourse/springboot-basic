package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static org.prgrms.springbootbasic.TypeOption.FIXED;
import static org.prgrms.springbootbasic.TypeOption.PERCENT;

@Component
public class Console {

    private boolean Running = true;
    private final Scanner scanner = new Scanner(System.in);
    private final VoucherService voucherService;

    @Autowired
    public Console(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    private String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

//    private void output(String output) {
//        System.out.println(output);
//    }

    public void run() {
        while (Running) {
            String menu = getInput("=== Voucher Program === \n" +
                    "Type **exit** to exit the program.\n" +
                    "Type **create** to create a new voucher.\n" +
                    "Type **list** to list all vouchers.");
            if (Menu.isExist(menu)) {
                Running = false;
                System.out.println("어플리케이션을 종료합니다");
                continue;
            } else if (!Menu.validate(menu)) {
                System.out.println("잘못된 입력입니다.");
                continue;
            }

            Request request = Request.GenerateRequest(menu);
            if (Menu.isCreateVoucher(menu)) {
                String option = getOption();
                String quantity = chooseOption(request, option);
                request.insertArgument("quantity", Long.parseLong(quantity));
            }

            System.out.println(voucherService.process(request));
        }
    }

    private String chooseOption(Request request, String option) {
        String quantity = "0";
        if (TypeOption.isFixed(option)) {
            quantity = getInput("amount: ");
            request.setOption(FIXED);
        } else if (TypeOption.isPercent(option)) {
            quantity = getInput("percent: ");
            request.setOption(PERCENT);
        }
        return quantity;
    }

    private String getOption() {
        String option;
        do {
            option = getInput("Choose one! fixedVoucher: 1 \nPercentVoucher: 2");
        } while (TypeOption.isFixed(option) && TypeOption.isPercent(option));
        return option;
    }
}
