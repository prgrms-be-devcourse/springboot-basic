package kr.co.springbootweeklymission.global.view;

import kr.co.springbootweeklymission.domain.model.VoucherPolicy;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Scanner;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Command inputCommand() {
        System.out.print("명령어를 입력해주세요. : ");
        return Command.from(SCANNER.nextLine());
    }

    public static VoucherPolicy inputVoucherPolicy() {
        System.out.print("할인 정책을 입력해주세요. : ");
        return VoucherPolicy.from(Integer.parseInt(SCANNER.nextLine()));
    }

    public static int inputAmount() {
        System.out.print("할인값을 입력해주세요. : ");
        return Integer.parseInt(SCANNER.nextLine());
    }
}
