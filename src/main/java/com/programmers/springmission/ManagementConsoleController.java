package com.programmers.springmission;

import com.programmers.springmission.customer.presentation.CustomerConsoleController;
import com.programmers.springmission.global.exception.InvalidInputException;
import com.programmers.springmission.view.Console;
import com.programmers.springmission.view.OptionType;
import com.programmers.springmission.voucher.presentation.VoucherConsoleController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;

/**
 * 최상위 단인 컨트롤러에서 내부적으로 동작이 실행될 때
 * 실행된 클래스, 메서드 이름, 발생 시각을 로그로 남긴다.
 *
 * @see com.programmers.springmission.global.aop.LoggerAspect
 */

@Slf4j
//@Component
public class ManagementConsoleController implements CommandLineRunner {

    private final Console console;
    private final VoucherConsoleController voucherConsoleController;
    private final CustomerConsoleController customerConsoleController;

    public ManagementConsoleController(Console console, VoucherConsoleController voucherConsoleController, CustomerConsoleController customerConsoleController) {
        this.console = console;
        this.voucherConsoleController = voucherConsoleController;
        this.customerConsoleController = customerConsoleController;
    }

    private boolean power = true;

    @Override
    public void run(String... args) {

        while (power) {
            try {
                console.outputDomainOption();
                OptionType inputValue = OptionType.of(console.input());

                switch (inputValue) {
                    case EXIT -> exitVoucherProgram();
                    case VOUCHER -> voucherConsoleController.run();
                    case CUSTOMER -> customerConsoleController.run();
                }
            } catch (InvalidInputException | IllegalArgumentException | DataAccessException invalidInputException) {
                console.output(invalidInputException.getMessage());
                log.warn(invalidInputException.getMessage());
            }
        }
    }

    private void exitVoucherProgram() {
        console.outputExit();
        power = false;
    }
}

