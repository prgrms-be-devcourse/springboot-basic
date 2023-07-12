package org.devcourse.springbasic.global.io.output.customer;

import org.devcourse.springbasic.global.io.console.customer.CustomerConsoleOutput;

import java.util.function.Supplier;

public enum CustomerOutputType {

    CONSOLE(CustomerConsoleOutput::new);

    private final Supplier<CustomerOutput> output;

    CustomerOutputType(Supplier<CustomerOutput> inputSupplier) {
        this.output = inputSupplier;
    }

    public CustomerOutput getOutput() {
        return output.get();
    }
}
