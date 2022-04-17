package org.prgrms.springbasic.service.console.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.utils.io.console.Console;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;
import static org.prgrms.springbasic.utils.enumm.ConsoleMessage.REGISTER_BLACK;


@Service("register")
@Slf4j
@RequiredArgsConstructor
public class RegisterBlackService implements ConsoleService {

    private final CustomerRepository repository;
    private final Console console;

    @Override
    public void execute() {
        console.printToConsole(REGISTER_BLACK.getMessage());
        var name = console.takeInput();
        var newBlackCustomer =  Customer.blackCustomer(randomUUID(), name);
        repository.save(newBlackCustomer);
    }


}
