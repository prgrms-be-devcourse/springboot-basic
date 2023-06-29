package org.programers.vouchermanagement.view;

import org.programers.vouchermanagement.member.domain.MemberStatus;
import org.programers.vouchermanagement.member.dto.response.MemberResponse;
import org.programers.vouchermanagement.member.dto.response.MembersResponse;
import org.programers.vouchermanagement.voucher.dto.response.VoucherResponse;
import org.programers.vouchermanagement.voucher.dto.response.VouchersResponse;
import org.programers.vouchermanagement.util.Converter;
import org.programers.vouchermanagement.wallet.dto.response.WalletResponse;
import org.programers.vouchermanagement.wallet.dto.response.WalletsResponse;

public class OutputView {

    private OutputView() {
    }

    public static void outputDomainType() {
        System.out.println("도메인 리스트");
        for (DomainType value : DomainType.values()) {
            System.out.printf("%d %s%n", value.getNumber(), value);
        }
        System.out.print("번호 입력 : ");
    }

    public static void outputCommand() {
        System.out.println("명령어 리스트");
        for (Command value : Command.values()) {
            System.out.printf("%d %s%n", value.getNumber(), value);
        }
        System.out.print("번호 입력 : ");
    }

    public static void outputDiscountPolicyType() {
        System.out.println("할인정책 리스트");
        for (DiscountPolicyType value : DiscountPolicyType.values()) {
            System.out.printf("%d %s%n", value.getNumber(), value);
        }
        System.out.print("번호 입력 : ");
    }

    public static void outputCommentAboutPolicy() {
        System.out.print("할인값 : ");
    }

    public static void outputVouchers(VouchersResponse response) {
        for (VoucherResponse voucher : response.getVouchers()) {
            System.out.printf("%s %s %s%n", voucher.getId(), voucher.getType(), voucher.getValue());
        }
    }

    public static void outputMemberStatus() {
        System.out.println("선택할 수 있는 회원 상태 리스트");
        for (MemberStatus value : MemberStatus.values()) {
            System.out.printf("%s%n", value);
        }
    }

    public static void outputUUIDComment() {
        System.out.print("수정/삭제할 도메인의 아이디 : ");
    }

    public static void outputMembers(MembersResponse response) {
        for (MemberResponse member : response.getMembers()) {
            System.out.println(Converter.toString(member));
        }
    }

    public static void outputVoucherIdComment() {
        System.out.print("바우처 아이디 입력 : ");
    }

    public static void outputMemberIdComment() {
        System.out.print("회원 아이디 입력 : ");
    }

    public static void outputWalletReadOption() {
        System.out.println("지갑 읽기 옵션 - 어떤 아이디로 조회할지 선택");
        for (WalletReadOption value : WalletReadOption.values()) {
            System.out.printf("%d %s%n", value.getNumber(), value);
        }
        System.out.print("번호 입력 : ");
    }

    public static void outputWallets(WalletsResponse response) {
        for (WalletResponse wallet : response.getWallets()) {
            System.out.println(String.format("id : %s | voucherId : %s | memberId :  %s",
                    wallet.getId(), wallet.getVoucher().getId(), wallet.getMember().getId()));
        }
    }
}
