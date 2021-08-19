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
                Optional<VoucherType> parsedType = parseVoucherType(inputVoucherType);

                if (parsedType.isEmpty()) break; // 다른 Type 입력
                long value = Long.parseLong(input.inputString(INPUT_PROMPT)); // TODO: 예외 처리,,, how to 잘?

                output.inputVoucherValue(parsedType.get());
                voucherService.createVoucher(
                        parsedType.get(),
                        UUID.randomUUID(),
                        value);
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

    private Optional<VoucherType> parseVoucherType(String inputVoucherType) {
        return switch (inputVoucherType) {
            case "PERCENT" -> Optional.of(VoucherType.PERCENTAGE);
            case "FIXED" -> Optional.of(VoucherType.FIXED);
            default -> Optional.empty();
        };
    }

}
