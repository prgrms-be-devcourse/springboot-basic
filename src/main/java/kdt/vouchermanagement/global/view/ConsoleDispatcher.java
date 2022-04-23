package kdt.vouchermanagement.global.view;

import kdt.vouchermanagement.domain.voucher.controller.VoucherConsoleController;
import kdt.vouchermanagement.domain.voucher.converter.VoucherRequestConverter;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.global.io.Input;
import kdt.vouchermanagement.global.response.Response;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleDispatcher implements ApplicationRunner {

    private final Input consoleInput;
    private final VoucherRequestConverter voucherRequestConverter;
    private final VoucherConsoleController voucherConsoleController;


    public ConsoleDispatcher(Input consoleInput, VoucherRequestConverter voucherRequestConverter, VoucherConsoleController voucherConsoleController) {
        this.consoleInput = consoleInput;
        this.voucherRequestConverter = voucherRequestConverter;
        this.voucherConsoleController = voucherConsoleController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            //TODO output
            Menu menu = findMenu(consoleInput.menuInput());
            switch (menu) {
                case EXIT_PROGRAM:
                    return;
                case CREATE_VOUCHER:
                    //TODO output
                    int voucherTypeNum = consoleInput.valueInput();
                    //TODO output
                    int discountValue = consoleInput.valueInput();
                    VoucherRequest voucherRequest = voucherRequestConverter.of(voucherTypeNum, discountValue);
                    Response response = voucherConsoleController.create(voucherRequest);
                    //TODO output
                case LIST_VOUCHERS:
                    //TODO output
                case BLACKLIST:
                    //TODO output
                case NONE:
                    //TODO output
            }
        }
    }

    private Menu findMenu(String inputMenu) {
        return Menu.from(inputMenu);
    }
}
