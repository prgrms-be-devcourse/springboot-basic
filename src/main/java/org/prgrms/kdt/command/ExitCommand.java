package org.prgrms.kdt.command;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 2:24 오전
 */
public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.exit(0);
    }
}
