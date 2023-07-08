package com.prgrms.view.view;

import com.prgrms.model.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Input {

    private final Scanner sc = new Scanner(System.in);

    public String enterOption() {
        return sc.next();
    }

    public Double enterDiscount() {
        return sc.nextDouble();
    }
}
