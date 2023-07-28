package org.devcourse.voucher.controller.console.runner;

import org.devcourse.voucher.controller.console.dto.Response;
import org.devcourse.voucher.controller.console.dto.Status;

public class ExitCommandRunner implements CommandRunner {

    @Override
    public Response run() {
        return new Response(Status.FINISH, "NO VALUE");
    }
}
