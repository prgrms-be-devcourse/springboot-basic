package com.mountain.voucherApp;

import com.mountain.voucherApp.adapter.in.VoucherAppController;
import com.mountain.voucherApp.shared.enums.Menu;
import com.mountain.voucherApp.shared.io.InputConsole;
import com.mountain.voucherApp.shared.io.OutputConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import static com.mountain.voucherApp.shared.constants.ProgramMessage.WRONG_INPUT;
import static com.mountain.voucherApp.shared.utils.MenuUtil.getMenuMap;
import static com.mountain.voucherApp.shared.utils.MenuUtil.isExit;

@Component
public class ExecuteEngine {
    private static final Logger log = LoggerFactory.getLogger(ExecuteEngine.class);
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherAppController voucherAppController;

    public ExecuteEngine(InputConsole inputConsole,
                         OutputConsole outputConsole,
                         VoucherAppController voucherAppController) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherAppController = voucherAppController;
    }

    public void run() {
        while (true) {
            outputConsole.printManual();
            try {
                int command = Integer.valueOf(inputConsole.input());
                Menu menu = getMenuMap().getOrDefault(command, null);
                if (menu != null) {
                    menu.exec(voucherAppController);
                    if (isExit(command))
                        break;
                } else {
                    log.error(WRONG_INPUT);
                    outputConsole.printMessage(WRONG_INPUT);
                }
            } catch (IllegalArgumentException | EmptyResultDataAccessException e) {
                outputConsole.printException(e);
            }
        }
    }
}
