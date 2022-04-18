package org.prgrms.kdt.domain.wallet.service;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.repository.CustomerRepository;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void removeVoucher(UUID voucherId) { //삭제
        voucherRepository.deleteById(voucherId);
        logger.info("Delete Voucher id: {}", voucherId);
    }

    @Transactional
    public void updateVoucherCustomerId(UUID voucherId, UUID customerId) { //할당
        int update = voucherRepository.updateCustomerId(voucherId, customerId);
        logger.info("Update Voucher's customerId count: {}", update);
    }

    @Transactional
    public List<Customer> getCustomerByTypeAndDate(VoucherType voucherType, LocalDate date) { //특정 바우처를 보유한 고객을 조회
        //바우처 찾기
        List<Customer> customers = new ArrayList<>();
        List<Voucher> vouchers = voucherRepository.findByVoucherTypeAndDate(voucherType, date);
        //찾은 바우처 id로 고객 찾기
        vouchers.stream()
                .map(voucher -> customerRepository.findByVoucherId(voucher.getVoucherId()))
                .forEach(customer -> customer.ifPresent(customers::add));
        logger.info("Get customers by type and date size: {}", customers.size());
        return customers;
    }

    @Transactional
    public List<Voucher> getVouchersByCustomerId(UUID customerId) { // 고객이 어떤 바우처를 보유하고 있는지 조회
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customerId);
        logger.info("Get voucher by customerId size: {}", vouchers.size());
        return vouchers;
    }
}
