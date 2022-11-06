package prgms.vouchermanagementapp.controller;

import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.Reader;
import prgms.vouchermanagementapp.io.Writer;

public class VoucherManagementController {

    private final Reader reader;
    private final Writer writer;
    private final RunningState state;

    public VoucherManagementController(Reader reader, Writer writer, RunningState state) {
        this.reader = reader;
        this.writer = writer;
        this.state = state;
    }

    public void run() {
        while (state.isRunning()) {
            writer.printCommandGuide();

            String command = reader.readLine();
            runUserRequest(command);
        }
    }

    public void runUserRequest(String command) {
        CommandType commandType = CommandType.of(command);

        if (commandType.isExit()) {
            writer.printExitMessage();
            state.exit();
            return;
        }
    }
}
