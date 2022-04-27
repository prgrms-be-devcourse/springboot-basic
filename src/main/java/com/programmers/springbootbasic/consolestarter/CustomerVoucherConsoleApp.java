package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.io.ConsoleInput;
import com.programmers.springbootbasic.io.ConsoleOutputFormatPrinter;
import com.programmers.springbootbasic.io.StandardInput;
import com.programmers.springbootbasic.io.StandardOutput;

import static com.programmers.springbootbasic.io.ConsoleInput.INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE;

public class CustomerVoucherConsoleApp implements ConsoleApp {

    private final CustomerManager customerManager;
    private final VoucherManager voucherManager;

    private final StandardInput input = new ConsoleInput();
    private final StandardOutput output = new ConsoleOutputFormatPrinter();
    private boolean on = true;

    public CustomerVoucherConsoleApp(CustomerManager customerManager, VoucherManager voucherManager) {
        this.customerManager = customerManager;
        this.voucherManager = voucherManager;
    }

    @Override
    public void run() {
        while(on) {
            showMainMenu();

            try {
                int response = Integer.parseInt(input.read());
                switch (response) {
                    case 1 -> customerManager.serviceCustomerWork();
                    case 2 -> voucherManager.serviceVoucherWork();
                    case 3 -> {
                        output.writeln("이용해 주셔서 감사합니다.");
                        on = false;
                    }
                    default -> output.writeln(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
                }
            }
            catch (NumberFormatException e) {
                output.writeln(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
            }
            catch (IllegalArgumentException e) {
                output.writeln(e.getMessage());
            }
        }
    }

    private void showMainMenu() {
        output.writeln("\n*************************************************************************************************************************");
        output.writeln("원하시는 업무를 선택하세요.");
        output.writeln("1. 고객");
        output.writeln("2. 할인권");
        output.writeln("3. 프로그램 종료");
        output.writeln("*************************************************************************************************************************");
        output.write("==> ");
    }

}