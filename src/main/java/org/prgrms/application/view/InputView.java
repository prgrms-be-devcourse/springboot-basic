package org.prgrms.application.view;

import org.prgrms.application.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class InputView {

    private final Scanner sc = new Scanner(System.in);

    public String selectCommandType() {
        String selection = sc.nextLine();
        return selection;
    }

    public VoucherType selectVoucherType() {
        String selection = sc.nextLine();
        Optional<VoucherType> voucherType = VoucherType.findBySelection(selection);

        return voucherType.orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

    }

    public String inputVoucherDetails() {
        String selection = sc.nextLine();
        return selection;
    }
}
