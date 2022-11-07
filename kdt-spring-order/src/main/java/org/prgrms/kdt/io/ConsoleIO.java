package org.prgrms.kdt.io;

import org.prgrms.kdt.CommandType;
import org.prgrms.kdt.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class ConsoleIO {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleIO.class);
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public String inputCommand() {
        try {
            return br.readLine().trim();
        } catch (IOException e) {
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
        }

        return "";
    }

    public String inputVoucherType() {
        try {
            System.out.println("생성할 Voucher 타입의 숫자를 입력하시오 :");
            System.out.println(VoucherType.getAllVoucherExpression());
            return br.readLine().trim();
        } catch (IOException e) {
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
        }

        return "";
    }

    public String inputVoucherDiscountValue() {
        try {
            System.out.println("선택한 Voucher 형식에 맞는 할인 금액 혹은 할인율을 입력하시오 : ");
            return br.readLine().trim();
        } catch (IOException e) {
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
        }

        return "";
    }

    public void printLine(String str) {
        System.out.println(str);
    }

    public void printEnableCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println(CommandType.getAllCommandExpression());
    }

    public void terminate() {
        System.out.println("프로그램을 종료합니다.");
    }

    public void printItems(List<String> vouchers) {

        if (vouchers.isEmpty()) {
            System.out.println("is empty");
        }

        vouchers.forEach(System.out::println);
    }

}
