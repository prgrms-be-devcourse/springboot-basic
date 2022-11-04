package prgms.voucherapplication.controller;

import prgms.voucherapplication.io.MenuType;
import prgms.voucherapplication.io.Reader;
import prgms.voucherapplication.io.Writer;

public class VoucherManagementController implements Runnable{
    private final Reader reader;
    private final Writer writer;

    public VoucherManagementController(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        while(true){
            writer.printLine(MenuType.getMessages());
            String input = reader.readLine();
        }
    }
}
