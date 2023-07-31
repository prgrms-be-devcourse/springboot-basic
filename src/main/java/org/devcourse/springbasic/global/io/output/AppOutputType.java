package org.devcourse.springbasic.global.io.output;

import org.devcourse.springbasic.global.io.console.AppConsoleOutput;

import java.util.function.Supplier;

public enum AppOutputType {

    CONSOLE(AppConsoleOutput::new);

    private final Supplier<AppOutput> output;

    AppOutputType(Supplier<AppOutput> inputSupplier) {
        this.output = inputSupplier;
    }

    public AppOutput getOutput() {
        return output.get();
    }
}
