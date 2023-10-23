package org.prgrms.kdt.app.configuration.io;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutputHandler implements OutputHandler{

    private static final String lineSeparator = System.lineSeparator();

    @Override
    public void outputStartMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Voucher Program ===");
        sb.append(lineSeparator);
        sb.append("Type exit to exit the program.");
        sb.append(lineSeparator);
        sb.append("Type create to create a new voucher.");
        sb.append(lineSeparator);
        sb.append("Type list to list all vouchers.");
        sb.append(lineSeparator);
        sb.append(lineSeparator);

        System.out.print(sb.toString());
    }

    @Override
    public void outputSystemMessage(String message) {
        System.out.print(message + lineSeparator);
    }

    @Override
    public void outputVouchers(List<Voucher> voucherList) {
        StringBuilder sb = new StringBuilder();

        voucherList.forEach(voucher -> {
            sb.append(voucher);
            sb.append(lineSeparator);
        });

        System.out.println(sb.toString());
    }
}
