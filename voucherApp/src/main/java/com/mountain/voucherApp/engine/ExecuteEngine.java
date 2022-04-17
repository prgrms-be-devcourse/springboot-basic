package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.enums.Menu;
import com.mountain.voucherApp.io.InputConsole;
import com.mountain.voucherApp.io.OutputConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mountain.voucherApp.constants.Message.WRONG_INPUT;
import static com.mountain.voucherApp.utils.MenuUtil.getMenuMap;
import static com.mountain.voucherApp.utils.MenuUtil.isExit;

@Component
public class ExecuteEngine {
    private static final Logger log = LoggerFactory.getLogger(ExecuteEngine.class);
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final Strategy strategy;

    public ExecuteEngine(InputConsole inputConsole, OutputConsole outputConsole, Strategy strategy) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.strategy = strategy;
    }

    public void run() {
        while (true) {
            outputConsole.printManual();
            String command = inputConsole.input().toLowerCase().trim();
            Menu menu = getMenuMap().getOrDefault(command, null);
            if (menu != null) {
                menu.exec(strategy);
                if (isExit(command))
                    break;
            } else {
                log.error(WRONG_INPUT);
                outputConsole.printWrongInput();
            }
        }
    }
}
