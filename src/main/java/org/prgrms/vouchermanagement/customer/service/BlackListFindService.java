package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.BlackListFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListFindService {

    private final BlackListFileRepository blackListFileRepository;

    @Autowired
    public BlackListFindService(BlackListFileRepository blackListFileRepository) {
        this.blackListFileRepository = blackListFileRepository;
    }

    public List<Customer> findAllBlackList() {
        return blackListFileRepository.findAll();
    }
}
