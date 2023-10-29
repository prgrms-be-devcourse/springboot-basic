package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutputHandler implements OutputHandler{

    private static final String lineSeparator = System.lineSeparator();

    @Override
    public void outputString(String string) {
        System.out.print(string + lineSeparator);
    }

    @Override
    public void outputCustomer(Customer customer) {
        System.out.println(customer);
    }

    @Override
    public void outputVoucher(Voucher voucher) {
        System.out.println(voucher);
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
