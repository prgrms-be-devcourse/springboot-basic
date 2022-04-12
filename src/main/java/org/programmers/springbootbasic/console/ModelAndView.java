package org.programmers.springbootbasic.console;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PACKAGE)
class ModelAndView {
    private final ConsoleResponseCode responseCode;
    private final String view;
    private final Model model;

    public ModelAndView(Model model, String view, ConsoleResponseCode responseCode) {
        this.model = model;
        this.view = view;
        this.responseCode = responseCode;
    }
}
