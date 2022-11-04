package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.MenuType;
import prgms.vouchermanagementapp.io.Reader;
import prgms.vouchermanagementapp.io.Writer;
import prgms.vouchermanagementapp.io.message.SystemMessage;

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
            writer.print(MenuType.getMessages());

            String menu = reader.readLine();
            runUserRequest(menu);
        }
    }

    public void runUserRequest(String menu) {
        MenuType menuType = MenuType.of(menu);

        if (menuType.equals(MenuType.EXIT)) {
            writer.print(SystemMessage.EXIT);
            state.exit();
        }
    }
}
