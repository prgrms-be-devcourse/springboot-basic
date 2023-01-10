package org.prgrms.kdt.io;

import org.prgrms.kdt.controller.CommandType;
import org.prgrms.kdt.exception.WrongInputDataException;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;

@Component
public class ConsoleIO {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String inputCommand() {
        try {
            return br.readLine().trim();
        } catch (IOException e) {
            throw new WrongInputDataException("Command Input 작업이 실패했습니다.", e);
        }
    }

    public String inputVoucherType() {
        try {
            System.out.println("생성할 Voucher 타입의 숫자를 입력하시오 :");
            System.out.println(VoucherType.getAllVoucherExpression());
            return br.readLine().trim();
        } catch (IOException e) {
            throw new WrongInputDataException("VoucherType Input 작업이 실패했습니다.", e);
        }
    }

    public String inputVoucherDiscountValue() {
        try {
            System.out.println("선택한 Voucher 형식에 맞는 할인 금액 혹은 할인율을 입력하시오 : ");
            return br.readLine().trim();
        } catch (IOException e) {
            throw new WrongInputDataException("VoucherDiscountValue Input 작업이 실패했습니다.", e);
        }
    }

    public String inputId(String entity) {
        try {
            System.out.println(MessageFormat.format("찾고자 하는 {0}의 ID를 입력하시오 :", entity));
            return br.readLine().trim();
        } catch (IOException e) {
            throw new WrongInputDataException("Id Input 작업이 실패했습니다.", e);
        }
    }

    public void printEnableCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println(CommandType.getAllCommandExpression());
    }

    public <T> void printItem(T voucher) {
        System.out.println("리스트 : ");
        System.out.println(voucher);
    }

    public <T> void printItems(List<T> vouchers) {
        System.out.println("리스트 : ");
        if (vouchers.isEmpty()) {
            System.out.println("is empty");
        }

        vouchers.forEach(System.out::println);
    }

    public void terminate() {
        System.out.println("프로그램을 종료합니다.");
    }
}
