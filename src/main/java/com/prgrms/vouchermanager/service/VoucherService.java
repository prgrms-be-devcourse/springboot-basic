package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.*;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.repository.CustomerRepository;
import com.prgrms.vouchermanager.repository.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        log.info(LogMessage.CHECK_VOUCHER_REPOSITORY.getMessage(), voucherRepository.getClass());
        log.info(LogMessage.CHECK_CUSTOMER_REPOSITORY.getMessage(), customerRepository.getClass());
    }

    public void create(VoucherType voucherType, long discount) {
        Voucher voucher = VoucherFactory.createVoucher(voucherType, discount).get();
        log.info(LogMessage.VOUCHER_INFO.getMessage(), voucher);

        voucherRepository.create(voucher);
    }

    public List<Voucher> list() {
        return voucherRepository.list();
    }

    public List<Customer> blacklist() {
        return customerRepository.blacklist();
    }
}
