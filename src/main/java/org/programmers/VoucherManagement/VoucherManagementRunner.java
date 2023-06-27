package org.programmers.VoucherManagement;

import lombok.RequiredArgsConstructor;
import org.programmers.VoucherManagement.io.Console;
import org.programmers.VoucherManagement.voucher.api.VoucherController;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherManagementRunner implements CommandLineRunner {
    private static Console console = new Console();
    private static CommandType commandType;
    private final VoucherController voucherController;

    @Override
    public void run(String... args) throws Exception {
        do {
            console.printType();
            commandType = console.readType();

            execute(commandType);
        } while (!commandType.isExit());
    }

    private void execute(CommandType commandType) {
        if (commandType.isCreate()) {
            console.printDiscountType();
            DiscountType discountType = console.readDiscountType();

            console.printDiscountValue();
            int discountValue = console.readDiscountValue();

            voucherController.createVoucher(new CreateVoucherRequest(discountType, discountValue));
        }
        if (commandType.isList()) {
            List<Voucher> voucherList = voucherController.getVoucherList();
            console.printVoucherList(voucherList);
        }
        if (commandType.isExit()) {
            console.printExitMessage();
        }
    }
}
