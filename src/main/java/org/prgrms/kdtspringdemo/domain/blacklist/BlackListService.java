package org.prgrms.kdtspringdemo.domain.blacklist;

import org.prgrms.kdtspringdemo.domain.blacklist.model.BlackCustomer;
import org.prgrms.kdtspringdemo.domain.blacklist.repository.BlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final BlackCustomerRepository blackCustomerRepository;

    public BlackListService(BlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public List<BlackCustomer> getAllBlackList() {
        return blackCustomerRepository.findAllBlackCustomers();
    }
}
