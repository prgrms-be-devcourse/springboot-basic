package org.devcourse.springbasic.global.io.input.customer;

import org.devcourse.springbasic.global.io.console.customer.CustomerConsoleInput;

import java.util.function.Supplier;

public enum CustomerInputType {
    CONSOLE(CustomerConsoleInput::new);

    private final Supplier<CustomerInput> input;

    CustomerInputType(Supplier<CustomerInput> inputSupplier) {
        this.input = inputSupplier;
    }

    public CustomerInput getInput() {
        return input.get();
    }
}
