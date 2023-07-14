package org.prgrms.kdt.commendLine;

import org.prgrms.kdt.member.dto.MembersResponse;
import org.prgrms.kdt.voucher.dto.VouchersResponse;
import org.prgrms.kdt.wallet.dto.response.JoinedWalletsResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.UUID;

@Component
public class Console {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public String getUserMenu() throws IOException {
        return br.readLine();
    }

    public String getVoucherTypes() throws IOException {
        System.out.print("1. FixedAmountVoucher 2. PercentDiscountVoucher\n " +
                "번호를 선택하세요: ");
        return br.readLine();
    }

    public String getDiscountAmount() throws IOException {
        System.out.print("할인 금액을 선택하세요: ");
        return br.readLine();
    }

    public String getMemberName() throws IOException {
        System.out.print("멤버 이름을 입력하세요: ");
        return br.readLine();
    }

    public UUID getMemberId() throws IOException {
        System.out.print("회원 id를 입력해주세요: ");
        return UUID.fromString(br.readLine());
    }

    public UUID getVoucherId() throws IOException {
        System.out.print("바우처 id를 입력해주세요: ");
        return UUID.fromString(br.readLine());
    }

    public UUID getWalletId() throws IOException {
        System.out.print("해당 지갑의 id를 입력해주세요: ");
        return UUID.fromString(br.readLine());
    }

    public void printMenu() {
        System.out.println("1. 프로그램 종료\n" +
                "2. 새로운 바우처 만들기\n" +
                "3. 모든 바우처 조회\n" +
                "4. 모든 블랙리스트 조회\n" +
                "5. 새 회원 만들기\n" +
                "6. 모든 회원 조회\n" +
                "7. 회원에게 바우처 할당\n" +
                "8. 회원이 보유한 바우처들 조회\n" +
                "9. 회원이 보유한 해당 바우처 삭제\n" +
                "10. 해당 바우처를 보유한 회원 조회\n" +
                "11. 모든 월렛 조회");
        System.out.print("번호를 입력하세요: ");
    }

    public void printAllVoucher(VouchersResponse vouchers) {
        vouchers.vouchers().forEach(e -> System.out.println(MessageFormat.format("{0},{1},{2}", e.voucherId(), e.voucherType(), e.amount())));
        System.out.println();
    }

    public void printAllMember(MembersResponse members) {
        members.members().forEach(e -> System.out.println(MessageFormat.format("{0},{1},{2}", e.memberId(), e.memberName(), e.memberStatus())));
        System.out.println();
    }

    public void printAllWallet(JoinedWalletsResponse joinedWalletsResponse) {
        joinedWalletsResponse.wallets().forEach(e -> System.out.println(MessageFormat.format("{0},{1},{2},{3}",
                e.walletId(), e.memberName(), e.voucherType(), e.voucherAmount())));
        System.out.println();
    }

    public static void printError() {
        System.out.println("잘못된 입력입니다. 다시 입력해 주십시오\n");
    }
}
