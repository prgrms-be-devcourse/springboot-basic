package org.prgrms.kdtspringdemo.blacklist;

import org.prgrms.kdtspringdemo.blacklist.model.BlackCustomer;
import org.prgrms.kdtspringdemo.blacklist.repository.BlackListRepository;
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
