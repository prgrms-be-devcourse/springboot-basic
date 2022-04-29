package org.devcourse.voucher.customer.service;

import org.devcourse.voucher.customer.Customer;
import org.devcourse.voucher.customer.repository.BlacklistRepository;
import org.devcourse.voucher.customer.repository.CsvBlacklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistServiceImpl implements BlacklistService {
    private final BlacklistRepository blacklistRepository;
    private final Logger logger = LoggerFactory.getLogger(BlacklistServiceImpl.class);

    public BlacklistServiceImpl(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public List<Customer> recallAllBlacklist() {
        logger.info("Service : Blacklist Inquiry");
        return blacklistRepository.findAll();
    }
}
