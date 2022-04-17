package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.enums.Menu;
import com.mountain.voucherApp.io.Console;
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
    private final Console console;
    private final Strategy strategy;

    @Autowired
    public ExecuteEngine(Console console, Strategy strategy) {
        this.console = console;
        this.strategy = strategy;
    }

    public void run() {
        while (true) {
            console.printManual();
            try {
                int command = Integer.valueOf(console.input());
                Menu menu = getMenuMap().getOrDefault(command, null);
                if (menu != null) {
                    menu.exec(strategy);
                    if (isExit(command))
                        break;
                } else {
                    log.error(WRONG_INPUT);
                    console.printWrongInput();
                }
            } catch (NumberFormatException e) {
                console.printException(e);
            }
        }
    }
}
