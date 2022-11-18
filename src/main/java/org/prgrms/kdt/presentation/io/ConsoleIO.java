package org.prgrms.kdt.presentation.io;

import org.prgrms.kdt.dao.entity.voucher.VoucherType;
import org.prgrms.kdt.exception.io.WrongInputDataException;
import org.prgrms.kdt.presentation.controller.CommandType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public void printEnableCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println(CommandType.getAllCommandExpression());
    }

    public void terminate() {
        System.out.println("프로그램을 종료합니다.");
    }

    public <T> void printItems(List<T> vouchers) {
        System.out.println("리스트 : ");
        if (vouchers.isEmpty()) {
            System.out.println("is empty");
        }

        vouchers.forEach(System.out::println);
    }
}
