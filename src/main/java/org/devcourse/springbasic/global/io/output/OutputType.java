package org.devcourse.springbasic.global.io.output;

import org.devcourse.springbasic.global.io.console.ConsoleOutput;

import java.util.function.Supplier;

public enum OutputType {

    CONSOLE(ConsoleOutput::new);

    private final Supplier<Output> output;

    OutputType(Supplier<Output> inputSupplier) {
        this.output = inputSupplier;
    }

    public Output getOutput() {
        return output.get();
    }
}
