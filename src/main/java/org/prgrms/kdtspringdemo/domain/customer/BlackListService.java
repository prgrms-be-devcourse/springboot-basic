package org.prgrms.kdtspringdemo.domain.customer;

import org.prgrms.kdtspringdemo.domain.customer.model.BlackCustomer;
import org.prgrms.kdtspringdemo.domain.customer.repository.BlackListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final BlackListRepository blackCustomerRepository;

    public BlackListService(BlackListRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public List<BlackCustomer> getAllBlackList() {
        return blackCustomerRepository.findAllBlackCustomers();
    }
}
