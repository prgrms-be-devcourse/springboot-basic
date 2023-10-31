package org.prgrms.vouchermanagement.blackCustomer;

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
