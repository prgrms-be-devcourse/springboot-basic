package org.prgms.voucher.ui;

import org.prgms.voucher.voucher.controller.VoucherController;
import org.prgms.voucher.voucher.domain.AmountVoucher;
import org.prgms.voucher.voucher.domain.VoucherOptionType;
import org.prgms.voucher.voucher.dto.VoucherCreateDto;
import org.prgms.voucher.voucher.ui.VoucherConsole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApplicationRunner implements CommandLineRunner {
    private final VoucherController voucherController;
    private final CommandLineApplication application;

    public ApplicationRunner(VoucherController voucherController, CommandLineApplication application) {
        this.voucherController = voucherController;
        this.application = application;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            application.printPromotionType(PromotionType.values());
            PromotionType promotionType = application.getPromotionType();
            runPromotionConsole(promotionType);

            application.printCommandType(CommandType.values());
            runByCommandType(promotionType, application.getCommandType());
        }
    }

    private void runPromotionConsole(PromotionType promotionType) {
        if (promotionType == PromotionType.VOUCHER) {
            new VoucherConsole().printSupportedCommands();
        }
    }

    private void runByCommandType(PromotionType promotionType, CommandType commandType) throws IOException {
        if (commandType == CommandType.EXIT) {
            System.exit(0);
        }

        if (promotionType == PromotionType.VOUCHER) {
            if (commandType == CommandType.CREATE) {
                application.printOptionType(VoucherOptionType.values());
                VoucherOptionType voucherOptionType = application.getOptionType();

                application.printInitialMoney();
                int initialMoney = application.getInitialMoney();

                application.printAmount();
                int discountAmount = application.getAmount();

                AmountVoucher amountVoucher = voucherController.createAmountVoucher(
                        new VoucherCreateDto(
                                voucherOptionType,
                                discountAmount,
                                initialMoney)
                );

                application.printCreateVoucher(amountVoucher);
            } else if (commandType == CommandType.LIST) {
                application.printList();
            }
        }
    }
}
