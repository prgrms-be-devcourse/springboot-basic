package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.List;

public class IOManager {

    private final Reader reader;
    private final Writer writer;

    public IOManager(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String askCommand() {
        writer.printCommandGuide();
        return reader.readLine();
    }

    public String askVoucherTypeIndex() {
        writer.printVoucherTypeGuide();
        return reader.readLine();
    }

    public Amount askFixedDiscountAmount() {
        writer.printFixedAmountGuide();
        long fixedDiscountAmount = readNumber();
        return new Amount(fixedDiscountAmount);
    }

    public Ratio askFixedDiscountRatio() {
        writer.printFixedDiscountRatioGuide();
        long fixedDiscountRatio = readNumber();
        return new Ratio(fixedDiscountRatio);
    }

    private long readNumber() {
        String inputNumber = reader.readLine();
        return Long.parseLong(inputNumber);
    }

    public void notifyExit() {
        writer.printExitMessage();
    }

    public void notifyVouchers(List<Voucher> vouchers) {
        writer.printVouchers(vouchers);
    }
}
