package org.programmers.springbootbasic.console.model;

import lombok.Getter;
import org.programmers.springbootbasic.console.ConsoleResponseCode;

@Getter
public class ConsoleModelAndView {
    private final ConsoleResponseCode responseCode;
    private final String view;
    private final ConsoleModel consoleModel;

    public ConsoleModelAndView(ConsoleModel consoleModel, String view, ConsoleResponseCode responseCode) {
        this.consoleModel = consoleModel;
        this.view = view;
        this.responseCode = responseCode;
    }
}