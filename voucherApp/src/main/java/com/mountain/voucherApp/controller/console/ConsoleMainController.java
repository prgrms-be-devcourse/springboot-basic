package com.mountain.voucherApp.controller.console;

import com.mountain.voucherApp.common.io.InputConsole;
import com.mountain.voucherApp.common.io.OutputConsole;
import com.mountain.voucherApp.model.enums.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.mountain.voucherApp.common.constants.ProgramMessage.WRONG_INPUT;
import static com.mountain.voucherApp.common.utils.MenuUtil.getMenuMap;
import static com.mountain.voucherApp.common.utils.MenuUtil.isExit;

@Controller
public class ConsoleMainController {
    private static final Logger log = LoggerFactory.getLogger(ConsoleMainController.class);

    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final VoucherConsoleController voucherConsoleController;

    public ConsoleMainController(InputConsole inputConsole,
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
