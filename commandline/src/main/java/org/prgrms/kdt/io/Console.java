package org.prgrms.kdt.io;

import org.prgrms.kdt.CommandType;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.InputException;
import org.prgrms.kdt.exception.IsNotNumberException;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class Console {
    public static final String NUMBER_REGEX = "^[\\+\\-]?\\d+$";
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
            String voucherType = br.readLine().trim();
            VoucherType.checkType(voucherType);
            return voucherType;

        } catch (IOException e) {
            throw new InputException(ErrorCode.InputException.getMessage());
        }
    }

    public Long inputVoucherDiscountValue() {
        System.out.print("선택한 Voucher 형식에 맞는 discountDegree(정수): ");
        try {
            String discountDegree = br.readLine().trim();
            isNumeric(discountDegree);
            return Long.parseLong(discountDegree);
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

    private void isNumeric(String number) {
        if (number == null) {
            throw new InputException(ErrorCode.InputException.getMessage());
        }
        if (!Pattern.matches(NUMBER_REGEX, number)) {
            throw new IsNotNumberException(ErrorCode.IS_NOT_NUMBER.getMessage());
        }
    }
}
