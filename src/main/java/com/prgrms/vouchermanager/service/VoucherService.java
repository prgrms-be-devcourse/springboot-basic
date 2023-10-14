package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.console.VoucherType;
import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.repository.CustomerRepository;
import com.prgrms.vouchermanager.repository.VoucherMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VoucherService {

    VoucherMemoryRepository voucherRepository;
    CustomerRepository customerRepository;
    @Autowired
    public VoucherService(VoucherMemoryRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public void create(VoucherType voucherType, long discount) {
        log.info(LogMessage.SERVICE_CREATE_START.getMessage());

        Voucher voucher = null;
        if(voucherType == VoucherType.FIXED) {
            voucher = new FixedAmountVoucher(discount);
        } else if(voucherType == VoucherType.PERCENT) {
            voucher = new PercentAmountVoucher(discount);
        }

        log.info(LogMessage.VOUCHER_TYPE_INFO.getMessage(), voucher);

        voucherRepository.create(voucher);
    }

    public List<Voucher> list() {
        return voucherRepository.list();
    }

    public List<Customer> blacklist() {
        return customerRepository.blacklist();
    }
}
