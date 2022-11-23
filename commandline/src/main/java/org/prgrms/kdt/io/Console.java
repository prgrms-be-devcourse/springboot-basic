package org.prgrms.kdt.io;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.InputException;
import org.prgrms.kdt.exception.IsNotNumberException;
import org.prgrms.kdt.util.ConstantMessageUtil;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class Console {
    public static final String NUMBER_REGEX = "^[\\+\\-]?\\d+$";
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String inputCommand() {
        try {
            return br.readLine().trim();
        } catch (IOException e) {
            throw new InputException(ErrorCode.INPUT_EXCEPTION.getMessage());
        }
    }

    public String inputVoucherType() {
        System.out.print(ConstantMessageUtil.TYPE_VOUCHER_INFO);
        try {
            String voucherType = br.readLine().trim();
            VoucherType.checkType(voucherType);
            return voucherType;

        } catch (IOException e) {
            throw new InputException(ErrorCode.INPUT_EXCEPTION.getMessage());
        }
    }

    public Long inputVoucherDiscountValue() {
        System.out.print(ConstantMessageUtil.DISCOUNT_VALUE);
        try {
            String discountDegree = br.readLine().trim();
            isNumeric(discountDegree);
            return Long.parseLong(discountDegree);
        } catch (IOException e) {
            throw new InputException(ErrorCode.INPUT_EXCEPTION.getMessage());
        }
    }

    public void printCommandList() {
        System.out.println(ConstantMessageUtil.VOUCHER_MAIN_MENU);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void terminate() {
        System.out.println(ConstantMessageUtil.TERMINATE);
    }

    public void initialize() {
        System.out.println(ConstantMessageUtil.INITIALIZE);
    }

    public void delete(){
        System.out.println(ConstantMessageUtil.DELETE);
    }

    public void printVouchers(List<Voucher> vouchers) {
        for (Voucher voucher : vouchers) {
            System.out.println(voucher);
        }
    }

    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    public Long inputVoucherId() {
        System.out.print(ConstantMessageUtil.INPUT_ID);
        try {
            String voucherId = br.readLine().trim();
            isNumeric(voucherId);
            return Long.parseLong(voucherId);
        } catch (IOException e) {
            throw new InputException(ErrorCode.INPUT_EXCEPTION.getMessage());
        }
    }

    public Long inputDiscountDegree() {
        System.out.print(ConstantMessageUtil.INPUT_DISCOUNT_DEGREE);
        try {
            String discountDegree = br.readLine().trim();
            isNumeric(discountDegree);
            return Long.parseLong(discountDegree);
        } catch (IOException e) {
            throw new InputException(ErrorCode.INPUT_EXCEPTION.getMessage());
        }
    }

    public void updateComplete() {
        System.out.println(ConstantMessageUtil.UPDATE);
    }

    private void isNumeric(String number) {
        if (number == null) {
            throw new InputException(ErrorCode.INPUT_EXCEPTION.getMessage());
        }
        if (!Pattern.matches(NUMBER_REGEX, number)) {
            throw new IsNotNumberException(ErrorCode.IS_NOT_NUMBER_EXCEPTION.getMessage());
        }
    }
}
