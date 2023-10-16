package devcourse.springbootbasic.commandline.function;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.console.constant.ConsoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FunctionHandler {

    private final ConsoleIOHandler consoleIOHandler;

    public void exit() {
        System.exit(0);
    }

    public void createVoucher() {
        consoleIOHandler.printMenuTitle(ConsoleConstants.CREATE_VOUCHER);
    }

    public void listAllVouchers() {
        consoleIOHandler.printMenuTitle(ConsoleConstants.LIST_ALL_VOUCHERS);
    }
}
