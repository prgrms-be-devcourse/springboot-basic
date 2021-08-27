package org.prgrms.kdt.commandapp;

import java.util.function.Predicate;

public interface Input {

    String input();

    String input(String msg, Predicate<String> p);
}
