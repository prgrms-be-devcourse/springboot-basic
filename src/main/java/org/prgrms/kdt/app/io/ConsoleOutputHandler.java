package org.prgrms.kdt.app.io;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutputHandler implements OutputHandler{

    private static final String lineSeparator = System.lineSeparator();

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

    @Override
    public void outputBlackList(List<Customer> customerList) {
        StringBuilder sb = new StringBuilder();

        customerList.forEach(customer -> {
            sb.append(customer);
            sb.append(lineSeparator);
        });

        System.out.println(sb.toString());
    }
}
