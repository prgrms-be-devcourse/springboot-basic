package org.prgrms.kdt.commendLine;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

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

    public String getMemberName() throws IOException {
        System.out.print("멤버 이름을 입력하세요.: ");
        return br.readLine();
    }

    public UUID getMemberId() throws IOException {
        System.out.print("회원 id를 입력해주세요.: ");
        return UUID.fromString(br.readLine());
    }

    public UUID getVoucherId() throws IOException {
        System.out.print("바우처 id를 입력해주세요.: ");
        return UUID.fromString(br.readLine());
    }

    public UUID getWalletId() throws IOException {
        System.out.print("해당 지갑의 id를 입력해주세요.: ");
        return UUID.fromString(br.readLine());
    }

    public void printMenu() {
        System.out.println("1. exit the program.\n" +
                "2. create a new voucher.\n" +
                "3. list all vouchers.\n" +
                "4. list all blackList\n" +
                "5. create a new member\n" +
                "6. assign a voucher\n" +
                "7. list of vouchers held by the member\n" +
                "8. delete voucher held by member\n" +
                "9. list of members with voucher");
    }

    public void printAllVoucher(List<Voucher> vouchers) {
        vouchers.forEach(e -> System.out.println(MessageFormat.format("{0},{1},{2}",e.getVoucherId() ,e.getVoucherType(), e.getDiscountPolicy().getAmount())));
        System.out.println();
    }

    public void printAllMember(List<Member> members) {
        members.forEach(e -> System.out.println(MessageFormat.format("{0},{1},{2}", e.getMemberId(),  e.getMemberName().getName(), e.getStatus())));
        System.out.println();
    }

    public static void printError() {
        System.out.println("잘못된 입력입니다. 다시 입력해 주십시오\n");
    }
}
