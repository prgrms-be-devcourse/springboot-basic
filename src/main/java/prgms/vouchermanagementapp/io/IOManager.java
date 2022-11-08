package prgms.vouchermanagementapp.io;

public class IOManager {

    private final Reader reader;
    private final Writer writer;

    public IOManager(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String getCommand() {
        writer.printCommandGuide();
        return reader.readLine();
    }

    public String getVoucherType() {
        writer.printVoucherTypeGuide();
        return reader.readLine();
    }

    public void notifyExit() {
        writer.printExitMessage();
    }
}
