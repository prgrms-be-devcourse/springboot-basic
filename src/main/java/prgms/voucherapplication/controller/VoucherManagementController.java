package prgms.voucherapplication.controller;

import prgms.voucherapplication.io.ExceptionMessage;
import prgms.voucherapplication.io.MenuType;
import prgms.voucherapplication.io.Reader;
import prgms.voucherapplication.io.Writer;

import java.util.NoSuchElementException;

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

            String menu = reader.readLine();
            runUserRequest(menu);
        }
    }

    private void runUserRequest(String menu) {
        if (!MenuType.isExist(menu)) {
            throw new NoSuchElementException(ExceptionMessage.NO_MENU_EXISTS.toString());
        }
    }
}
