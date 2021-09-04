package com.example.kdtspringmission.view;

import com.example.kdtspringmission.customer.domain.Customer;
import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputView implements OutputView{

    @Override
    public void commandList() {
        System.out.println("=== Voucher Program ===\n"
            + "Type 'exit' to exit the program\n"
            + "Type 'create' to create voucher\n"
            + "Type 'list' to list vouchers\n"
            + "Type 'blacklist' to list blacklist\n"
            + "Type 'customers' to list customers\n"
            + "Type 'assign_voucher' to assign a voucher to a customer\n"
            + "Type 'list_by_owner' to list vouchers the customer has\n"
            + "Type 'delete_voucher' to remove voucher from the customer's wallet");
    }

    @Override
    public void creatableVoucherList() {
        System.out.println("Which one? (1.FixedAmountVoucher, 2.RateAmountVoucher)");
    }

    @Override
    public void voucherList(List<Voucher> vouchers) {
        System.out.println(vouchers);
    }

    @Override
    public void customerList(List<Customer> customers) {
        System.out.println("===== Registered customers =====");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}
