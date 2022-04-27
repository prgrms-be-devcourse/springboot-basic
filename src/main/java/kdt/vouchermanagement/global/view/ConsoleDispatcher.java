package kdt.vouchermanagement.global.view;

import kdt.vouchermanagement.domain.voucher.controller.VoucherConsoleController;
import kdt.vouchermanagement.domain.voucher.converter.VoucherRequestConverter;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.global.io.Input;
import kdt.vouchermanagement.global.io.Output;
import kdt.vouchermanagement.global.response.Response;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleDispatcher implements ApplicationRunner {

    private final Input consoleInput;
    private final Output consoleOutput;
    private final VoucherRequestConverter voucherRequestConverter;
    private final VoucherConsoleController voucherConsoleController;


    public ConsoleDispatcher(Input consoleInput, Output consoleOutput, VoucherRequestConverter voucherRequestConverter, VoucherConsoleController voucherConsoleController) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.voucherRequestConverter = voucherRequestConverter;
        this.voucherConsoleController = voucherConsoleController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            consoleOutput.printMenu(CommandMessage.MENU.getMessage());
            Menu menu = findMenu(consoleInput.menuInput());
            switch (menu) {
                case EXIT_PROGRAM:
                    return;
                case CREATE_VOUCHER:
                    commandCreateVoucher();
                    break;
                case LIST_VOUCHERS:
                    commandListVouchers();
                    break;
                case NONE:
                    consoleOutput.printMenu(CommandMessage.NONE.getMessage());
                    break;
            }
        }
    }

    private void commandCreateVoucher() {
        consoleOutput.printMenu(CommandMessage.VOUCHER_NUM.getMessage());
        int voucherTypeNum = consoleInput.valueInput();
        consoleOutput.printMenu(CommandMessage.DISCOUNT_VALUE.getMessage());
        int discountValue = consoleInput.valueInput();
        VoucherRequest request = voucherRequestConverter.of(voucherTypeNum, discountValue);

        Response response = voucherConsoleController.create(request);
        consoleOutput.printResponse(response);
    }

    private void commandListVouchers() {
        Response response = voucherConsoleController.getVouchers();
        consoleOutput.printResponse(response);
    }

    private Menu findMenu(String inputMenu) {
        return Menu.from(inputMenu);
    }
}
