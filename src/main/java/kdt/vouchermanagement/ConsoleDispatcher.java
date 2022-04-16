package kdt.vouchermanagement;

import kdt.vouchermanagement.io.Input;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class ConsoleDispatcher implements ApplicationRunner {

    private final Input consoleInput;

    public ConsoleDispatcher(Input consoleInput) {
        this.consoleInput = consoleInput;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            //TODO output
            try {
                Menu menu = findMenu(excuteInput());
                switch (menu) {
                    case EXIT_PROGRAM:
                        return;
                    case CREATE_VOUCHER:
                        //TODO output
                        String inputVoucherTypeNum = excuteInput();
                        //TODO output
                        String inputDiscountValue = excuteInput();
                        //TODO controller
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

    public String excuteInput() {
        String inputValue = consoleInput.input();
        if (inputValue.isBlank()) {
            throw new IllegalArgumentException("공백이 입력되었습니다. 올바른 값을 입력해주세요.");
        }
        return inputValue;
    }

    public Menu findMenu(String input) {
        return Menu.from(input);
    }
}
