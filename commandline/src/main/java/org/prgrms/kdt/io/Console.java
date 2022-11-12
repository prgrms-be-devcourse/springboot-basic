package org.prgrms.kdt.io;

import org.prgrms.kdt.CommandType;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.InputException;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class Console {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String inputCommand() {
        try {
            return br.readLine().trim();
        } catch (IOException e) {
            throw new InputException(ErrorCode.InputException.getMessage());
        }
    }

    public String inputVoucherType() {
        System.out.println(VoucherType.getAllVoucherExpression());
        System.out.print("생성할 Voucher 타입의 숫자를 입력하세요: ");
        try {
            return br.readLine().trim();
        } catch (IOException e) {
            throw new InputException(ErrorCode.InputException.getMessage());
        }
    }

    public String inputVoucherDiscountValue() {
        System.out.print("선택한 Voucher 형식에 맞는 discountDegree(정수): ");
        try {
            return br.readLine().trim();
        } catch (IOException e) {
            throw new InputException(ErrorCode.InputException.getMessage());
        }
    }

    public void printCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println(CommandType.getAllCommandExpression());
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void terminate() {
        System.out.println("프로그램을 종료합니다.");
    }

    public void printVouchers(List<Voucher> vouchers) {
        for (Voucher voucher : vouchers) {
            System.out.println(voucher);
        }
    }
}
