package org.prgrms.kdt.service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.prgrms.kdt.model.Customer;
import org.prgrms.kdt.repository.CustomerRepository;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private Map<UUID, Customer> blacklists;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    private void loadAllBlackListCustomer() {
        blacklists = customerRepository.findAllBlackListCustomer();
        printBlackLists(blacklists);

    }

    private void printBlackLists(Map<UUID, Customer> blacklists) {
        System.out.println("==== black list customers ====");
        blacklists.forEach((key, value) -> System.out.println(
            MessageFormat.format("{0}({1})", value.getName(), key)));
        System.out.println("==============================");
    }

}
