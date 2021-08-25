package org.prgrms.kdt;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.engine.io.Console;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.prgrms.kdt.engine.voucher.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherService;
import org.prgrms.kdt.engine.voucher.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CommandLineApplication {
    private static final String PROFILE = "local";
    private static final Input input = new Console();
    private static final Output output = new Console();
    private static final AnnotationConfigApplicationContext applicationContext;
    private static final VoucherService voucherService;

    static {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        applicationContext.getEnvironment().setActiveProfiles(PROFILE);
        applicationContext.refresh();
        voucherService = applicationContext.getBean(VoucherService.class);
    }

    public static void main(String[] args) {
        output.help();
        while (true) {
            String command = input.inputCommand("Command : ");
            if (!CommandType.has(command)) {
                output.illegalInputError();
                continue;
            }

            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            switch (commandType) {
                case CREATE:
                    output.showVoucherOptions();
                    String typeName = input.inputCommand("");
                    Optional<Voucher> voucher = createVoucher(typeName);
                    voucher.ifPresentOrElse(output::createVoucher, output::illegalInputError);
                    break;

                case LIST:
                    Optional<Map<UUID, Voucher>> voucherList = voucherService.listVoucher();
                    voucherList.ifPresentOrElse(output::listVoucher, output::voucherListNotFoundError);
                    break;

                case EXIT:
                    System.exit(0);
                    break;
            }
        }
    }

    private static Optional<Voucher> createVoucher(String typeName) {
        VoucherType type;
        long rate;

        try {
            type = VoucherType.valueOf(typeName.toUpperCase());
            rate = Long.parseLong(input.inputCommand("rate : "));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        return Optional.of(voucherService.createVoucher(type, rate));
    }
}
