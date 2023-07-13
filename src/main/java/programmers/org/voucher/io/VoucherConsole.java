package programmers.org.voucher.io;

import programmers.org.voucher.dto.VoucherResponse;

import java.util.List;
import java.util.Scanner;

public class VoucherConsole {

    private final Scanner scanner = new Scanner(System.in);

    public void printManual() {
        System.out.println("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n" +
                "Type find to find a voucher.\n" +
                "Type update to edit a voucher.\n" +
                "type delete to remove a voucher");
    }

    public void printVoucherList(List<VoucherResponse> voucherList) {
        voucherList.forEach(this::printVoucher);
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

    public long inputVoucherId() {
        System.out.println("바우처 ID 입력: ");
        return scanner.nextLong();
    }

    public void printVoucher(VoucherResponse voucher) {
        System.out.println("VoucherID : " + voucher.getId()
                + " Type : " + voucher.getType()
                + " DiscountAmount : " + voucher.getDiscountAmount());
    }
}
