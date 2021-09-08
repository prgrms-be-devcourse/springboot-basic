package org.prgrms.kdtspringorder.io.implementation;

import org.prgrms.kdtspringorder.customer.domain.Customer;
import org.prgrms.kdtspringorder.io.abstraction.Output;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ConsoleOutput implements Output {

    @Override
    public void printVoucherList(List<Voucher> listToPrint) {
        for (Voucher voucher : listToPrint) {
            System.out.println(voucher);
        }
    }

    @Override
    public void printVoucher(Voucher voucher) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("------------------------------------------------------------")
                .append("\nVoucher ID : ").append(voucher.getId())
                .append("\nVoucher Type : ").append(voucher.getVoucherTypeInString())
                .append("\n------------------------------------------------------------");
        System.out.println(stringBuilder);
    }

    @Override
    public void printCustomerList(List<Customer> customers) {
        for (Customer customer : customers) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Customer ID : ");
            stringBuilder.append(customer.getId());
            System.out.println(stringBuilder);
        }
    }

    @Override
    public void print(String string) {
        System.out.println(string);
    }

}
