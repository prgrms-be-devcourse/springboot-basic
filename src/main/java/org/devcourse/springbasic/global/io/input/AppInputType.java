package org.devcourse.springbasic.global.io.input;

import org.devcourse.springbasic.global.io.console.AppConsoleInput;

import java.util.function.Supplier;

public enum AppInputType {
    CONSOLE(AppConsoleInput::new);

    private final Supplier<AppInput> input;

    AppInputType(Supplier<AppInput> inputSupplier) {
        this.input = inputSupplier;
    }

    public AppInput getInput() {
        return input.get();
    }
}
