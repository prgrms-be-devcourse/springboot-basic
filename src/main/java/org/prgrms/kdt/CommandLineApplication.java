package org.prgrms.kdt;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.engine.io.Console;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.prgrms.kdt.engine.voucher.Voucher;
import org.prgrms.kdt.engine.voucher.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CommandLineApplication {
    private static final Input input = new Console();
    private static final Output output = new Console();
    private static final String profile = "prod";
    private static final AnnotationConfigApplicationContext applicationContext;
    private static final VoucherService voucherService;

    static {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        applicationContext.getEnvironment().setActiveProfiles(profile);
        applicationContext.refresh();
        voucherService = applicationContext.getBean(VoucherService.class);
    }

    public static void main(String[] args) {
        output.help();
        while (true) {
            String command = input.inputCommand("Command : ");
            switch (command) {
                case "create" :
                    output.showVoucherOptions();
                    String type = input.inputCommand("");
                    Optional<Voucher> voucher = createVoucher(type);
                    voucher.ifPresentOrElse(output::createVoucher, output::inputError);
                    break;

                case "list" :
                    Optional<Map<UUID, Voucher>> voucherList = voucherService.listVoucher();
                    voucherList.ifPresentOrElse(output::listVoucher, output::voucherListError);
                    break;

                case "exit" :
                    System.exit(0);
                    break;

                default :
                    output.inputError();
            }
        }
    }

    static Optional<Voucher> createVoucher(String type) {
        if (type.equals("fixed")) {
            long amount = Long.parseLong(input.inputCommand("amount : "));
            return Optional.of(voucherService.createFixedVoucher(amount));
        }
        if (type.equals("percent")) {
            long percent = Long.parseLong(input.inputCommand("percent : "));
            return Optional.of(voucherService.createPercentVoucher(percent));
        }
        return Optional.empty();
    }
}
