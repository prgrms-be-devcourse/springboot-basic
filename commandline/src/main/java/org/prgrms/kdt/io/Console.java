package org.prgrms.kdt.io;

import org.prgrms.kdt.CommandType;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class Console {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String inputCommand() throws IOException {
        return br.readLine().trim();
    }

    public String inputVoucherType() throws IOException {
        System.out.println(VoucherType.getAllVoucherExpression());
        System.out.print("생성할 Voucher 타입의 숫자를 입력하세요: ");
        return br.readLine().trim();
    }

    public String inputVoucherDiscountValue() throws IOException {
        System.out.print("선택한 Voucher 형식에 맞는 discountDegree(정수): ");
        return br.readLine().trim();
    }

    public void printCommandList() {
        System.out.println("=== Voucher Program ===");
        System.out.println(CommandType.getAllCommandExpression());
    }

    public void printError(ErrorCode errorCode) {
        System.out.println(errorCode.getMessage());
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
