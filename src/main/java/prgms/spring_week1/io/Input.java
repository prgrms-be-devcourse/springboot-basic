package prgms.spring_week1.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    private static final Logger log = LoggerFactory.getLogger(Input.class);
    Scanner sc = new Scanner(System.in);

    public String inputTextOption() {
        return sc.nextLine();
    }

    public String inputVoucherType() {
        return sc.nextLine();
    }

    public Long insertDiscountAmountVoucher() {
        return sc.nextLong();
    }

    public int insertDiscountPercentVoucher() {
        return sc.nextInt();
    }

}
