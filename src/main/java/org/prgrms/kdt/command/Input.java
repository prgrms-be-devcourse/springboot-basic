package org.prgrms.kdt.command;

import java.util.function.Predicate;

public interface Input {

    String input();

    String input(Predicate<String> p);

    String input(String msg, Predicate<String> p);
}
