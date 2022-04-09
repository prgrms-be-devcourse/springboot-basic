package org.prgrms.kdt.io;

import org.prgrms.kdt.model.Function;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class Input {
    private final Scanner sc;

    Input() {
        this.sc = new Scanner(System.in);
    }

    public Optional<String> inputFunction() {
        String inputString = sc.nextLine();
        if (Function.hasFunction(inputString)) {
            return Optional.of(inputString.trim());
        }
        return Optional.empty();
    }
}
