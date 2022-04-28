package com.mountain.voucherApp;

import com.mountain.voucherApp.adapter.in.console.VoucherConsoleController;
import com.mountain.voucherApp.shared.enums.Menu;
import com.mountain.voucherApp.shared.io.InputConsole;
import com.mountain.voucherApp.shared.io.OutputConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.mountain.voucherApp.shared.constants.ProgramMessage.WRONG_INPUT;
import static com.mountain.voucherApp.shared.utils.MenuUtil.getMenuMap;
import static com.mountain.voucherApp.shared.utils.MenuUtil.isExit;

@Controller
public class ExecuteEngine {
    private static final Logger log = LoggerFactory.getLogger(ExecuteEngine.class);
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherConsoleController voucherConsoleController;

    public ExecuteEngine(InputConsole inputConsole,
                         OutputConsole outputConsole,
                         VoucherConsoleController voucherConsoleController) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherConsoleController = voucherConsoleController;
    }

    @RequestMapping("/consoleApp")
    public String consoleApp() {
        return "/consoleApp/main";
    }

    @RequestMapping("/consoleApp/exec")
    public String run() {
        while (true) {
            outputConsole.printManual();
            try {
                int command = Integer.valueOf(inputConsole.input());
                Menu menu = getMenuMap().getOrDefault(command, null);
                if (menu != null) {
                    menu.exec(voucherConsoleController);
                    if (isExit(command))
                        return "/consoleApp/exit";
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
