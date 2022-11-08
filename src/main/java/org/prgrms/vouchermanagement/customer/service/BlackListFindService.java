package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.BlackListCustomerRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class BlackListFindService {

    private final BlackListCustomerRepository blackListCustomerRepository;

    private final Logger logger = getLogger(BlackListFindService.class);

    @Autowired
    public BlackListFindService(BlackListCustomerRepository blackListCustomerRepository) {
        this.blackListCustomerRepository = blackListCustomerRepository;
    }

    public List<Customer> findAllBlackList() {

        logger.info("블랙리스트 조회");

        return blackListCustomerRepository.findAll();
    }
}
