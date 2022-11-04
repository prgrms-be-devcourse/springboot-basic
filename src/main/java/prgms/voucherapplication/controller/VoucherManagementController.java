package prgms.voucherapplication.controller;

import prgms.voucherapplication.io.MenuType;
import prgms.voucherapplication.io.Reader;
import prgms.voucherapplication.io.Writer;

public class VoucherManagementController implements Runnable{
    private final Reader reader;
    private final Writer writer;
    private final RunningState state;

    public VoucherManagementController(Reader reader, Writer writer, RunningState state) {
        this.reader = reader;
        this.writer = writer;
        this.state = state;
    }

    @Override
    public void run() {
        while(state.isRunning()){
            writer.printLine(MenuType.getMessages());
            String input = reader.readLine();
        }
    }
}
