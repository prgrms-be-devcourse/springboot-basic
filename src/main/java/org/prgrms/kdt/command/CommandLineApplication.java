package org.prgrms.kdt.command;

import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CommandLineApplication implements Runnable {

    private static final String INPUT_PROMPT = "> ";

    private final Input input;
    private final Output output;
    private final ApplicationContext applicationContext;
    private final VoucherService voucherService;

    public CommandLineApplication(Console console, ApplicationContext applicationContext) {
        this.input = console;
        this.output = console;
        this.applicationContext = applicationContext;
        voucherService = applicationContext.getBean(VoucherService.class);
    }

    @Override
    public void run() {
        output.init(); // command 설명
        while (excute()) ; // 실행
    }

    // TODO: Refactoring Command Pattern
    private boolean excute() {
        String inputString = input.inputString(INPUT_PROMPT);
        switch (inputString) {
            case "create":
                output.inputVoucherType();
                String inputVoucherType = input.inputString(INPUT_PROMPT);
                // TODO: Refactoring to Factory Pattern
                switch (inputVoucherType) { // 와 세상에 ㅋㅋㅋ
                    case "PERCENT":
                        output.inputVoucherAmount(VoucherType.PERCENTAGE);
                        voucherService.createVoucher(
                                VoucherType.PERCENTAGE,
                                UUID.randomUUID(),
                                Long.parseLong(input.inputString(INPUT_PROMPT)));
                        break;
                    case "FIXED":
                        output.inputVoucherAmount(VoucherType.FIXED);
                        voucherService.createVoucher(
                                VoucherType.PERCENTAGE,
                                UUID.randomUUID(),
                                Long.parseLong(input.inputString(INPUT_PROMPT)));
                        break;
                    default:
                        break;
                }
                break;
            case "list":
                List<Voucher> vouchers = voucherService.getAllVoucher();
                output.voucherList(vouchers);
                break;
            case "exit":
                output.exit();
                return false;
            default:

                break;
        }
        return true;
    }

    public Optional<CommandType> parseInputString(String command) {
        return switch (command) {
            case "create" -> Optional.of(CommandType.CREATE);
            case "list" -> Optional.of(CommandType.LIST);
            case "exit" -> Optional.of(CommandType.EXIT);
            default -> Optional.empty();
        };
    }

}
