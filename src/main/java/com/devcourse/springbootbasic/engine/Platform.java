package com.devcourse.springbootbasic.engine;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.io.OutputConsole;
import com.devcourse.springbootbasic.engine.model.Menu;

public class Platform implements Runnable {

    private final InputConsole input;
    private final OutputConsole output;

    public Platform(InputConsole inputConsole, OutputConsole outputConsole) {
        this.input = inputConsole;
        this.output = outputConsole;
    }

    @Override
    public void run() {
        while (true) {
            if (getUserMenuInput()) {
                break;
            }
        }
    }

    private boolean getUserMenuInput() {
        try {
            Menu menu = input.inputMenu();
            return branchByMenu(menu);
        } catch (InvalidDataException e) {
            output.printError(e);
        }
        return false;
    }

    private boolean branchByMenu(Menu menu) {
        return switch (menu) {
            case EXIT -> {
                output.endPlatform();
                yield true;
            }
            case CREATE -> {
                output.printMessage(Menu.CREATE.toString());
                yield false;
            }
            case LIST -> {
                output.printMessage(Menu.LIST.toString());
                yield false;
            }
        };
    }
}
