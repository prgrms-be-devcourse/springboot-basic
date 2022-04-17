package kdt.vouchermanagement.global.view;

import kdt.vouchermanagement.domain.voucher.controller.VoucherConsoleController;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequestDto;
import kdt.vouchermanagement.global.io.Input;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleDispatcher implements ApplicationRunner {

    private Input consoleInput;
    private VoucherConsoleController voucherConsoleController;

    public ConsoleDispatcher(Input consoleInput, VoucherConsoleController voucherConsoleController) {
        this.consoleInput = consoleInput;
        this.voucherConsoleController = voucherConsoleController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            //TODO output
            try {
                Menu menu = findMenu(consoleInput.input());
                switch (menu) {
                    case EXIT_PROGRAM:
                        return;
                    case CREATE_VOUCHER:
                        //TODO output
                        String voucherTypeNum = consoleInput.input();
                        //TODO output
                        String discountValue = consoleInput.input();
                        voucherConsoleController.create(new VoucherRequestDto(voucherTypeNum, discountValue));
                    case LIST_VOUCHERS:
                        //TODO output
                    case BLACKLIST:
                        //TODO output
                }
            } catch (Exception e) {
                //TODO output
            }
        }
    }

    private Menu findMenu(String inputMenu) {
        return Menu.from(inputMenu);
    }
}
