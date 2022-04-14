package org.prgrms.springbasic.service.console.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.console.Console;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service("register")
@Slf4j
public class RegisterBlackService implements ConsoleService {

    private final CustomerRepository repository;
    private final Console console;

    @Autowired
    public RegisterBlackService(CustomerRepository repository, Console console) {
        this.repository = repository;
        this.console = console;
    }

    @Override
    public void execute() {
        console.printNameInput();
        var name = console.takeInput();
        var newBlackCustomer =  Customer.blackCustomer(UUID.randomUUID(), name);
        repository.save(newBlackCustomer);
    }


}
