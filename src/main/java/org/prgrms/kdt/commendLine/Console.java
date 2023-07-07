package org.prgrms.kdt.commendLine;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;

@Component
public class Console {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String getUserMenu() throws IOException {
        return br.readLine();
    }

    public String getVoucherTypes() throws IOException {
        System.out.print("1. FixedAmountVoucher 2. PercentDiscountVoucher\n " +
                "번호를 선택하세요:");
        return br.readLine();
    }

    public String getDiscountAmount() throws IOException {
        System.out.print("할인 금액을 선택하세요:");
        return br.readLine();
    }

    public void printMenu() {
        System.out.println("Type -exit- to exit the program.\n" +
                "Type -create- to create a new voucher.\n" +
                "Type -list- to list all vouchers.\n" +
                "Type -blacklist- to list all blackList");
    }

    public void printAllBoucher(List<Voucher> vouchers) {
        vouchers.forEach(e -> System.out.println(MessageFormat.format("{0},{1},{2}",e.getVoucherId() ,e.getVoucherType(), e.getDiscountPolicy().getAmount())));
        System.out.println();
    }

    public void printAllBlackList(List<Member> blackList) {
        blackList.forEach(e -> System.out.println(e.getMemberName().getName()));
        System.out.println();
    }

    public static void printError() {
        System.out.println("잘못된 입력입니다. 다시 입력해 주십시오\n");
    }
}
