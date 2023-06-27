package programmers.org.voucher.io;

import programmers.org.voucher.domain.Voucher;

import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public void printManual() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    public void printVoucherList(List<Voucher> voucherList) {
        voucherList.forEach(System.out::println);
    }

    public void printInputCommandError() {
        System.out.println("유효하지 않은 커맨드입니다.");
    }

    public void printInputVoucherTypeError() {
        System.out.println("유효하지 않은 바우처 타입입니다.");
    }

    public String inputCommand() {
        return scanner.next();
    }

    public String inputVoucherType() {
        System.out.println("바우처 타입 선택(fixed or percent): ");
        return scanner.next();
    }

    public int inputVoucherInfo() {
        System.out.println("바우처 정보 입력: ");
        return scanner.nextInt();
    }
}
