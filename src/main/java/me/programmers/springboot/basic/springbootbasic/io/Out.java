package me.programmers.springboot.basic.springbootbasic.io;

import me.programmers.springboot.basic.springbootbasic.customer.model.Customer;

import java.util.List;

public class Out implements ConsoleOutput {

    @Override
    public void output(String message) {
        System.out.println(message);
    }

    @Override
    public void output(List<Customer> list) {
        list.forEach(customer -> {
            System.out.println("Customer Id: " + customer.getCustomerId());
            System.out.println("Customer name: " + customer.getCustomerInfo().getName());
            System.out.println("Customer email: " + customer.getCustomerInfo().getEmail());
            System.out.println();
        });
    }

    public void showMenu() {
        System.out.println("=== Voucher Program ===\n" +
                "1. Type exit to exit the program.\n" +
                "2. Type create to create a new voucher.\n" +
                "3. Type list to list all vouchers.\n" +
                "4. Type update to update voucher.\n" +
                "5. Type delete to delete all vouchers.\n" +
                "6. Type customer_insert to create a new customer\n" +
                "7. Type customer_list to find all customers\n" +
                "8. Type customer_update to update customer\n" +
                "9. Type customer_delete to delete all customers\n" +
                "10. Type customer_findby_email to delete all customers\n"
        );
    }
}
