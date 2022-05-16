package com.mountain.voucherapp.controller.console;

import com.mountain.voucherapp.common.io.InputConsole;
import com.mountain.voucherapp.common.io.OutputConsole;
import com.mountain.voucherapp.model.enums.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.mountain.voucherapp.common.constants.ProgramMessage.WRONG_INPUT;
import static com.mountain.voucherapp.model.enums.Menu.isExit;

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
                int seq = Integer.parseInt(inputConsole.input());
                Optional<Menu> optionalMenu = Menu.find(seq);
                if (optionalMenu.isPresent()) {
                    Menu menu = optionalMenu.get();
                    menu.exec(voucherConsoleController);
                    if (isExit(seq))
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
