package org.prgms.w3d1;

import org.prgms.w3d1.io.Input;
import org.prgms.w3d1.io.Output;
import org.prgms.w3d1.model.blacklist.BlacklistService;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.PercentDiscountVoucher;
import org.prgms.w3d1.model.voucher.VoucherService;
import org.prgms.w3d1.util.Command;
import org.prgms.w3d1.util.VoucherCommand;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class CommandLineApplication implements Runnable {

    private final Input input;
    private final Output output;
    private final ConfigurableApplicationContext applicationContext;

    public CommandLineApplication(Input input, Output output, ConfigurableApplicationContext applicationContext) {
        this.input = input;
        this.output = output;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        while (true) {
            selectService(input.input("""
                    === Select Service ===.
                    1. VoucherService
                    2. BlacklistService
                    """));
        }
    }

    private void selectService(String input) {
        Command command = Command.getCommand(input);

        switch (command) {
            case VOUCHER_SERVICE -> startVoucherService();
            case BLACKLIST_SERVICE -> startBlackListService();
            default -> output.inputError();
        }
    }

    private void startVoucherService() {
        var VoucherService = applicationContext.getBean(VoucherService.class);
        String command = input.input("""
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """);
        executeVoucherService(VoucherService, command);
    }

    private void executeVoucherService(VoucherService voucherService, String input) {
        Command command = Command.getCommand(input);

        switch (command) {
            case EXIT -> {
                applicationContext.close();
                System.exit(0);
            }
            case CREATE -> executeCreateVoucher(voucherService);
            case LIST -> System.out.println(voucherService.findAll());
            default -> output.inputError();
        }
    }

    private void executeCreateVoucher(VoucherService voucherService) {
        // 분기의 분기 -> 메서드를 만들자
        // if -> switch변경, enum화
        String str = input.input("""
                === Create Voucher ===
                1 : FixedAmountVoucher
                2 : PercentDiscountVoucher
                """);
        VoucherCommand command = VoucherCommand.getCommand(str);

        switch (command) {
            case FIXED_AMOUNT_VOUCHER -> {
                output.printFixedMenu();
                long discount = Long.parseLong(input.input("Enter discount value : "));
                // 스태틱 of 메서드로 구현
                voucherService.saveVoucher(FixedAmountVoucher.of(UUID.randomUUID(), discount));
                // Ctrl + Alt + L : 자동정렬
            }
            case PERCENT_DISCOUNT_VOUCHER -> {
                output.printPercentMenu();
                long discount = Long.parseLong(input.input("Enter discount percent : "));
                voucherService.saveVoucher(PercentDiscountVoucher.of(UUID.randomUUID(), discount));
            }
            default -> output.inputError();
        }
    }


    private void startBlackListService() {
        var blacklistService = applicationContext.getBean(BlacklistService.class);
        String str = input.input("""
                === Blacklist Menu ===
                Type exit to exit the program.
                Type create to create a new Blacklist.
                Type list to list all Blacklists.
                """);
        Command command = Command.getCommand(str);

        switch (command) {
            case EXIT -> {
                applicationContext.close();
                System.exit(0);
            }

            case CREATE -> {
                output.printBlackListCreateMenu();
                String name = input.input("");
                blacklistService.save(UUID.randomUUID(), name);
            }
            case LIST -> System.out.println(blacklistService.findAll());

            default -> output.inputError();
        }
    }
}
