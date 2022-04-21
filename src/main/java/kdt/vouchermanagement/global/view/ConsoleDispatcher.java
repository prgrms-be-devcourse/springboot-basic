package kdt.vouchermanagement.global.view;

import kdt.vouchermanagement.domain.voucher.converter.VoucherRequestConverter;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.global.io.Input;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleDispatcher implements ApplicationRunner {

    private final Input consoleInput;
    private final VoucherRequestConverter voucherRequestConverter;


    public ConsoleDispatcher(Input consoleInput, VoucherRequestConverter voucherRequestConverter) {
        this.consoleInput = consoleInput;
        this.voucherRequestConverter = voucherRequestConverter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            //TODO output
            Menu menu = findMenu(consoleInput.menuInput());
            try {
                switch (menu) {
                    case EXIT_PROGRAM:
                        return;
                    case CREATE_VOUCHER:
                        //TODO output
                        int voucherTypeNum = consoleInput.valueInput();
                        //TODO output
                        int discountValue = consoleInput.valueInput();
                        VoucherRequest voucherRequest = voucherRequestConverter.of(voucherTypeNum, discountValue);
                    case LIST_VOUCHERS:
                        //TODO output
                    case BLACKLIST:
                        //TODO output
                    case NONE:
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
