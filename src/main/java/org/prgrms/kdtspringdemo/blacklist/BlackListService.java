package org.prgrms.kdtspringdemo.blacklist;

import org.prgrms.kdtspringdemo.blacklist.model.BlackCustomer;
import org.prgrms.kdtspringdemo.blacklist.repository.FileBlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private final FileBlackCustomerRepository blackCustomerRepository;

    public BlackListService(FileBlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public List<BlackCustomer> getAllBlackList() {
        return blackCustomerRepository.findAllBlackCustomers();
    }
}
