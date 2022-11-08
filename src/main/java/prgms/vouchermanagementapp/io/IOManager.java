package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.model.FixedAmount;

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

    public FixedAmount askFixedAmount() {
        writer.printFixedAmountGuide();
        String inputAmount = reader.readLine();
        int amount = Integer.parseInt(inputAmount);
        return new FixedAmount(amount);
    }

    public void notifyExit() {
        writer.printExitMessage();
    }
}
