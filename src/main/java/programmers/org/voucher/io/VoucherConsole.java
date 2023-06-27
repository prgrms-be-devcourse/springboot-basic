package programmers.org.voucher.io;

import programmers.org.voucher.domain.Voucher;

import java.util.List;

public class VoucherConsole {

    private final Input input;
    private final Output output;

    public VoucherConsole(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void printManual() {
        output.print("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }

    public void printVoucherList(List<Voucher> voucherList) {
        voucherList.forEach(this::printVoucher);
    }

    public void printError(String error) {
        output.print(error);
    }

    public String inputCommand() {
        return input.read();
    }

    public String inputVoucherType() {
        output.print("바우처 타입 선택(fixed or percent): ");
        return input.read();
    }

    public int inputVoucherInfo() {
        output.print("바우처 정보 입력: ");
        return input.readInt();
    }

    private void printVoucher(Voucher voucher) {
        output.print("VoucherID : " + voucher.getVoucherId()
                + " Type : " + voucher.getVoucherType()
                + " DiscountAmount : " + voucher.getDiscountAmount());
    }
}
