package org.prgms.management;

import org.prgms.management.blacklist.service.BlacklistService;
import org.prgms.management.command.CommandExecutor;
import org.prgms.management.io.Input;
import org.prgms.management.io.Output;
import org.prgms.management.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication {
    private final Output output;
    private final Input input;
    private final VoucherService voucherService;
    private final BlacklistService blacklistService;

    public ConsoleApplication(Output output, Input input, VoucherService voucherService, BlacklistService blacklistService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
        this.blacklistService = blacklistService;
    }

    public void run() {
        boolean flag = true;

        System.out.println("=== 바우처 프로그램 ===");
        while (flag) {
            output.init();
            String command = input.getInput("명령어를 입력해주세요 : ");
            flag = CommandExecutor.execute(command, input, output, blacklistService, voucherService);
        }
    }
}
