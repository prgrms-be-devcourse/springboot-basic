package org.devcourse.springbasic.global.io.input;

import org.devcourse.springbasic.global.io.console.ConsoleInput;

import java.util.function.Supplier;

public enum InputType {
    CONSOLE(ConsoleInput::new);

    private final Supplier<Input> input;

    InputType(Supplier<Input> inputSupplier) {
        this.input = inputSupplier;
    }

    public Input getInput() {
        return input.get();
    }
}
