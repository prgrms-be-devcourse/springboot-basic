package org.prgms.voucheradmin.domain.voucherwallet.service;

import java.util.List;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucherwallet.dao.VoucherWalletRepository;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.prgms.voucheradmin.global.exception.customexception.CustomerNotFoundException;
import org.prgms.voucheradmin.global.exception.customexception.VoucherNotFoundException;
import org.prgms.voucheradmin.global.exception.customexception.VoucherWalletNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 바우처 집갑에 대한 로직을 수행하는 클래스입니다.
 */
@Service
public class VoucherWalletService {
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherWalletRepository voucherWalletRepository;

    public VoucherWalletService(CustomerRepository customerRepository, VoucherRepository voucherRepository, VoucherWalletRepository voucherWalletRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
        this.voucherWalletRepository = voucherWalletRepository;
    }

    /**
     * 고객에게 바우처를 할당하는 메서드 입니다.
     */
    @Transactional
    public VoucherWallet createVoucherWallet(UUID customerId, UUID voucherId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        return voucherWalletRepository.create(new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
    }

    /**
     * 고객에게 할당된 바우처를 조회하는 메서드입니다.
     */
    @Transactional(readOnly = true)
    public List<Voucher> getAllocatedVouchers(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return voucherRepository.findAllocatedVouchers(customer.getCustomerId());
    }

    /**
     * 특정 바우처를 보유한 고객을 조회하는 메서드입니다.
     */
    public List<Customer> getVoucherOwners(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotFoundException(voucherId));

        return customerRepository.findVoucherOwners(voucher.getVoucherId());
    }

    /**
     * 고객이 보유한 바우처를 제거하는 메서드 입니다.
     */
    public void deleteVoucherWallet(UUID customerId, UUID voucherId) {
        VoucherWallet voucherWallet = voucherWalletRepository.findByCustomerIdAndVoucherId(customerId, voucherId).orElseThrow(() -> new VoucherWalletNotFoundException(customerId, voucherId));
        voucherWalletRepository.deleteVoucherWallet(voucherWallet);
    }
}
