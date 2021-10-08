package org.prgrms.kdt.command.service.customer;

import org.prgrms.kdt.command.ValueValidation;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomerCreateService implements CommandService {

    private final CustomerService customerService;

    public CustomerCreateService(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void commandRun() {
        customerService.addCustomer(new Customer(
                UUID.randomUUID(),
                createName(),
                createEmail(),
                LocalDateTime.now()
        ));

        System.out.println("customer가 생성되었습니다.");
    }

    public String createName() {
        System.out.println("customer_name을 입력해주세요.");
        String name;

        while (true) {
            name = Input.input();
            if (ValueValidation.nameValidation(name)) return name;
        }
    }

    public String createEmail() {
        System.out.println("email을 입력해주세요.");
        String email;

        while (true) {
            email = Input.input();
            if (ValueValidation.emailValidation(email))
                return email;
        }
    }
}