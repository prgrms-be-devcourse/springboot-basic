package org.prgrms.vouchermanagement.blackCustomer.service;

import org.prgrms.vouchermanagement.blackCustomer.domain.BlackCustomer;
import org.prgrms.vouchermanagement.blackCustomer.repository.BlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackCustomerService {

    private final BlackCustomerRepository blackCustomerRepository;

    public BlackCustomerService(BlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public List<BlackCustomer> getBlackList() {
        return blackCustomerRepository.load();
    }
}
