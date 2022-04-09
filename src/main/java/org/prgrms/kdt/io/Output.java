package org.prgrms.kdt.io;

import org.prgrms.kdt.model.Function;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class Output {
    public void printFunctions() {
        System.out.println("=== Voucher Program ===");
        for(Function function : Function.values()) {
            System.out.println(MessageFormat.format("Type {0}{1}.", function.name(), function.getExplain()));
        }
    }
}
