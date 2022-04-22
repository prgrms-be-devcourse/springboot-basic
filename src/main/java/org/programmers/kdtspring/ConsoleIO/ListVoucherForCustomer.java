package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.programmers.kdtspring.service.VoucherService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ListVoucherForCustomer implements CommandStrategy {

    private final CustomerRepository customerRepository;
    private final VoucherService voucherService;
    private final Input input;
    private final Scanner scanner = new Scanner(System.in);

    public ListVoucherForCustomer(Input input, VoucherService voucherService, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.voucherService = voucherService;
        this.input = input;
    }

    @Override
    public void runCommand() {
        String email = input.inputEmail();
        Optional<Customer> customer = customerRepository.findByEmail(email);
        List<Voucher> vouchers = voucherService.findVoucherForCustomer(customer.get().getCustomerId());

        vouchers.forEach(System.out::println);
    }
}
