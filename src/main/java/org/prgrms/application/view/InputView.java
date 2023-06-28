package org.prgrms.application.view;

import org.prgrms.application.CommandType;
import org.prgrms.application.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class InputView {

    private final Scanner sc = new Scanner(System.in);

    public CommandType selectCommandType() {
        String selection = sc.nextLine();
        CommandType commandType = CommandType.findBySelection(selection);

        return commandType;
    }

    public VoucherType selectVoucherType() {
        String selection = sc.nextLine();
        VoucherType voucherType = VoucherType.findBySelection(selection);

        return voucherType;

    }

    public double inputVoucherDetails() {
        double voucherDetail = sc.nextDouble();
        sc.nextLine();
        return voucherDetail;
    }
}
