package programmers.org.voucher.io;

import programmers.org.voucher.domain.Voucher;

import java.util.List;
import java.util.Scanner;

public class VoucherConsole {

    private final Scanner scanner = new Scanner(System.in);

    public void printManual() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    public void printVoucherList(List<Voucher> voucherList) {
        voucherList.forEach(this::printVoucher);
    }

    public void printError(String error) {
        System.out.println(error);
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

    private void printVoucher(Voucher voucher) {
        System.out.println("VoucherID : " + voucher.getVoucherId()
                + " Type : " + voucher.getVoucherType()
                + " DiscountAmount : " + voucher.getDiscountAmount());
    }
}
