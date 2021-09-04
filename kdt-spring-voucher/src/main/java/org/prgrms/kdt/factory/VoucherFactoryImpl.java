package org.prgrms.kdt.factory;

import org.apache.tomcat.util.buf.StringUtils;
import org.prgrms.kdt.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.utill.IO.IoConsole;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Stream;

@Component
public class VoucherFactoryImpl implements VoucherFactory {

    private static final long MAX_DISCOUNT_VALUE = 1000000;

    private IoConsole ioConsole = new IoConsole();

    @Override
    public Voucher getDiscounterVoucher(int voucherStatus) {

        Voucher voucher = null;
        switch (voucherStatus) {
            case 1:
                voucher = setVoucherType("FIXED");
                break;
            case 2:
                voucher = setVoucherType("PERCENT");
                break;
            default:
                ioConsole.inputError();
                break;
        }
        return voucher;
    }

    public Voucher setVoucherType(String type){
        ioConsole.message(type+" AmountVoucher를 선택하셨습니다.");
        var discount = type.equals("FIXED") ? Long.parseLong(isDiscountNum(ioConsole)) : Long.parseLong(isPercentNum(ioConsole));
        var voucher = VoucherStatus.valueOf(type).createVoucher(discount);
        ioConsole.message(type+" Voucher 생성이 완료되었습니다.");
        return voucher;
    }

    public String isDiscountNum(IoConsole ioConsole) {
        String s1 = ioConsole.inputText("할인하고자 하는 금액을 입력해 주세요");
        boolean realNum = s1.chars().allMatch(Character::isDigit);
        if (realNum) {
            long pNum = Long.parseLong(s1);
            if (pNum < 1 || pNum > MAX_DISCOUNT_VALUE) {
                ioConsole.message("1 ~ 1000000원 이하의 금액을 적어주세요");
                return isDiscountNum(ioConsole);
            }
            return s1;
        }
        ioConsole.message("숫자가 아닙니다! 다시 입력하세욧!!!");
        return isDiscountNum(ioConsole);
    }

    public String isPercentNum(IoConsole ioConsole) {
        String s1 = ioConsole.inputText("할인하고자 하는 퍼센트(%)를 입력해 주세요");
        boolean realNum = s1.chars().allMatch(Character::isDigit);
        if (realNum) {
            long pNum = Long.parseLong(s1);
            if (pNum < 0L || pNum > 100L) {
                ioConsole.message("1~100사이의 숫자를 입력해주세요!");
                return isPercentNum(ioConsole);
            }
            return s1;
        }
        ioConsole.message("숫자가 아닙니다! 다시 입력하세욧!!!");
        return isPercentNum(ioConsole);
    }


}