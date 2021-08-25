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


    @Override
    public Voucher getDiscounterVoucher(VoucherStatus voucherStatus) {
        IoConsole ioConsole = new IoConsole();

        Voucher voucher = null;
        switch (voucherStatus) {
            case FIXED:
                ioConsole.message("FixedAmountVoucher를 선택하셨습니다.");
                String discount = isDiscountNum(ioConsole);
                voucher = new FixedAmountVoucher(UUID.randomUUID(), Long.parseLong(discount));
                ioConsole.message("Fixed 바우처 생성이 완료되었습니다.");
                break;
            case PERCENT:
                ioConsole.message("PercentAmountVoucher를 선택하셨습니다.");
                String percent = isPercentNum(ioConsole);
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(percent));
                ioConsole.message("Percent 바우처 생성이 완료되었습니다.");
                break;
            default:
                ioConsole.inputError();
                break;
        }
        return voucher;
    }

    public String isDiscountNum(IoConsole ioConsole){
        String s1 = ioConsole.inputText("할인하고자 하는 금액을 입력해 주세요");
        boolean realNum = s1.chars().allMatch(Character::isDigit);
        if(realNum){
            long pNum = Long.parseLong(s1);
            if(pNum < 1 || pNum > MAX_DISCOUNT_VALUE){
                ioConsole.message("1 ~ 1000000원 이하의 금액을 적어주세요");
                return isDiscountNum(ioConsole);
            }
            return s1;
        }
        ioConsole.message("숫자가 아닙니다! 다시 입력하세욧!!!");
        return isDiscountNum(ioConsole);
    }

    public String isPercentNum(IoConsole ioConsole){
        String s1 = ioConsole.inputText("할인하고자 하는 퍼센트(%)를 입력해 주세요");
        boolean realNum = s1.chars().allMatch(Character::isDigit);
        if(realNum){
            long pNum = Long.parseLong(s1);
            if(pNum < 0L || pNum > 100L){
               ioConsole.message("1~100사이의 숫자를 입력해주세요!");
               return isPercentNum(ioConsole);
            }
            return s1;
        }
        ioConsole.message("숫자가 아닙니다! 다시 입력하세욧!!!");
        return isPercentNum(ioConsole);
    }



}