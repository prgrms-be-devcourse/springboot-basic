package kr.co.springbootweeklymission.view;

import kr.co.springbootweeklymission.member.domain.model.MemberStatus;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Command inputCommand() {
        log.info("명령어를 입력해주세요.");
        return Command.from(Integer.parseInt(SCANNER.nextLine()));
    }

    public static String inputVoucherPolicy() {
        log.info("할인 정책의 번호를 입력해주세요.");
        return VoucherPolicy.from(Integer.parseInt(SCANNER.nextLine())).toString();
    }

    public static int inputAmount() {
        log.info("할인값을 입력해주세요.");
        return Integer.parseInt(SCANNER.nextLine());
    }

    public static String inputVoucherId() {
        log.info("바우처 아이디를 입력해주세요.");
        return SCANNER.nextLine();
    }

    public static MemberStatus inputMemberStatus() {
        log.info("회원 상태를 입력해주세요.");
        return MemberStatus.valueOf(SCANNER.nextLine());
    }

    public static String inputMemberId() {
        log.info("회원 아이디를 입력해주세요.");
        return SCANNER.nextLine();
    }
}
