package org.programmers.springbootbasic.console.model;

import lombok.Getter;
import org.programmers.springbootbasic.console.ConsoleResponseCode;

@Getter
public class ModelAndView {
    private final ConsoleResponseCode responseCode;
    private final String view;
    private final Model model;

    public ModelAndView(Model model, String view, ConsoleResponseCode responseCode) {
        this.model = model;
        this.view = view;
        this.responseCode = responseCode;
    }
}