package org.prgrms.springbasic.service.console.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.utils.io.console.Console;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.prgrms.springbasic.utils.exception.NotExistData;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.prgrms.springbasic.utils.enumm.ErrorMessage.NO_CUSTOMER;


@Service("blacks")
@Slf4j
@RequiredArgsConstructor
public class ListBlackService implements ConsoleService {

    private final CustomerRepository repository;
    private final Console console;

    @Override
    public void execute() {
        var customers = validateCustomers(repository.findAll());
        customers.forEach(console::printToConsole);
    }

    private List<Customer> validateCustomers(List<Customer> vouchers) {
        if(vouchers.size() == 0) {
            log.error("Can't find any customer.");
            throw new NotExistData(NO_CUSTOMER.getMessage());
        }

        return vouchers;
    }
}
