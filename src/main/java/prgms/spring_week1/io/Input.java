package prgms.spring_week1.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Input {
    private static final Logger log = LoggerFactory.getLogger(Console.class);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String inputTextOption() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    public String inputVoucherType() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    public Long insertDiscountAmountVoucher() {
        try {
            long discountAmount = Long.parseLong(br.readLine());
            return discountAmount;
        } catch (IOException e) {
            return -1L;
        }
    }

    public int insertDiscountPercentVoucher() {
        try {
            Integer discountPercent = Integer.parseInt(br.readLine());
            return discountPercent;
        } catch (IOException e) {
            return 0;
        }
    }

}
