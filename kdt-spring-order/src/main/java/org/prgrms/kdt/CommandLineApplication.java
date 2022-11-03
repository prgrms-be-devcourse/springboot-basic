package org.prgrms.kdt;

import org.prgrms.kdt.io.ConsoleIO;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.voucher.VoucherValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CommandLineApplication {

    private final ConsoleIO consoleIO;
    private final VoucherService voucherService;

    public CommandLineApplication(ConsoleIO consoleIO, VoucherService voucherService) {
        this.consoleIO = consoleIO;
        this.voucherService = voucherService;
    }

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(CommandLineApplication.class, args)){
            CommandLineApplication application = context.getBean(CommandLineApplication.class);
            application.run();
        }
    }

    public void run() {
        while (true) {
            consoleIO.printEnableCommandList();
            try {
                CommandStatus command = CommandStatus.of(consoleIO.inputCommand());
                switch (command) {
                    case CREATE -> {
                        String voucherType = consoleIO.inputVoucherType();
                        String voucherDiscountValue = consoleIO.inputVoucherDiscountValue();
                        VoucherValidator.validateVoucherStatusAndDiscountValue(voucherType, voucherDiscountValue);
                        voucherService.save(voucherService.create(voucherType, voucherDiscountValue));
                    }
                    case LIST -> consoleIO.printItems(voucherService.getAllVouchers());
                    case EXIT -> {
                        consoleIO.terminate();
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                consoleIO.printLine(e.getMessage());
            }
        }
    }
}
