package org.prgrms.springbasic.service.console;

import org.prgrms.springbasic.repository.customer.CustomerRepository;

public class CommandService {
    private final CustomerRepository repository;

    public CommandService(CustomerRepository repository) {
        this.repository = repository;
    }

    enum ABC {

        ABC {

        }
    }
}

