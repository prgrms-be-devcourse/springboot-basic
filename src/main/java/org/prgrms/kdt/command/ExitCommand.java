package org.prgrms.kdt.command;

import org.prgrms.kdt.io.Console;

/**
 * Created by yhh1056
 * Date: 2021/08/28 Time: 2:09 오후
 */
public class ExitCommand implements CommandOperator {

    @Override
    public void operate(Console console) {
        console.printExit();
        System.exit(0);
    }
}
