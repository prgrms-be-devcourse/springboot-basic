package org.prgrms.kdt;

import org.prgrms.kdt.io.Console;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 12:34 오전
 */
public class CommandLineApplication implements Runnable {

    private final Console console;

    public CommandLineApplication(Console console) {
        this.console = console;
    }

    @Override
    public void run() {
        console.guide();
    }
}
