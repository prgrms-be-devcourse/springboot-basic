package org.prgrms.springbootbasic;

import org.prgrms.springbootbasic.message.Request;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.type.Menu;
import org.prgrms.springbootbasic.type.TypeOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Scanner;

@Component
public class Console {

    public static final String MENU_NOTIFICATION = """
            === Voucher Program ===\s
            Type **exit** to exit the program.
            Type **create** to create a new voucher.
            Type **list** to list all vouchers.""";
    public static final String WRONG_INPUT_NOTIFICATION = "wrong input!";
    public static final String VOUCHER_TYPE_CHOICE_NOTIFICATION = "Choose one!\nfixedVoucher: 1 \nPercentVoucher: 2";
    public static final String EXIT_NOTIFICATION = "Good bye!";
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

    public void run() {
        while (Running) {
            String menu = getInput(MENU_NOTIFICATION);
            if (Menu.isExist(menu)) {
                Running = false;
                System.out.println(EXIT_NOTIFICATION);
                continue;
            } else if (!Menu.validate(menu)) {
                System.out.println(WRONG_INPUT_NOTIFICATION);
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
        TypeOption typeOption = TypeOption.stringToTypeOption(option);
        String quantity = getInput(MessageFormat.format("Type {0} :", typeOption.getArgument()));
        request.setOption(typeOption);
        return quantity;
    }

    private String getOption() {
        String option;
        do {
            option = getInput(VOUCHER_TYPE_CHOICE_NOTIFICATION);
        } while (TypeOption.isFixed(option) && TypeOption.isPercent(option));
        return option;
    }
}
