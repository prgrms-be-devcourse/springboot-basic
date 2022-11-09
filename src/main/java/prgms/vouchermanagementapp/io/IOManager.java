package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.model.Amount;

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
        String inputAmount = reader.readLine();
        long fixedDiscountAmount = Long.parseLong(inputAmount);
        return new Amount(fixedDiscountAmount);
    }

    public void notifyExit() {
        writer.printExitMessage();
    }
}
