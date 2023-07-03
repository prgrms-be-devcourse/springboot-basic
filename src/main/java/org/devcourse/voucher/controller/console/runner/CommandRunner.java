package org.devcourse.voucher.controller.console.runner;

import org.devcourse.voucher.controller.console.Command;

public interface CommandRunner {

    String run();

    boolean isSupport(Command command);

}
