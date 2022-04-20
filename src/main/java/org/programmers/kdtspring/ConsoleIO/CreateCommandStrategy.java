package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.service.VoucherService;

import java.util.Optional;
import java.util.Scanner;

public class CreateCommandStrategy implements CommandStrategy {

    private final Input input;
    private final VoucherService voucherService;
    private final CustomerRepository customerRepository;
    private final Scanner scanner = new Scanner(System.in);

    public CreateCommandStrategy(Input input, VoucherService voucherService, CustomerRepository customerRepository) {
        this.input = input;
        this.voucherService = voucherService;
        this.customerRepository = customerRepository;
    }

    @Override
    public void runCommand() {
        String chosenVoucher = input.chooseVoucher();
        String email = input.inputEmail();
        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (chosenVoucher.equalsIgnoreCase(String.valueOf(VoucherType.FixedAmountVoucher))) {
            int amount = scanner.nextInt();
            voucherService.createFixedAmountVoucher(customer, amount);
        }
        if (chosenVoucher.equalsIgnoreCase(String.valueOf(VoucherType.PercentDiscountVoucher))) {
            int percent = scanner.nextInt();
            voucherService.createPercentDiscountVoucher(percent);
        }

    }
}
