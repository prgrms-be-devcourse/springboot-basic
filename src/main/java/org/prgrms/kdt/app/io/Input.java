package org.prgrms.kdt.app.io;

import java.util.function.Predicate;

public interface Input {

    String input();

    String input(String msg);

    String input(Predicate<String> p);

    String input(String msg, Predicate<String> p);
}
