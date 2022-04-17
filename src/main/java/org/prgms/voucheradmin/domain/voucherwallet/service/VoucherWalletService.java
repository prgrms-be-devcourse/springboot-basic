package org.prgms.voucheradmin.domain.voucherwallet.service;

import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucherwallet.dao.VoucherWalletRepository;
import org.prgms.voucheradmin.domain.voucherwallet.dto.CreatVoucherWalletReqDto;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
import org.prgms.voucheradmin.global.exception.CustomerNotFoundException;
import org.prgms.voucheradmin.global.exception.VoucherNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
     * 바우처를 고객에게 할당하는 메서드 입니다.
     */
    @Transactional
    public VoucherWallet createVoucherWallet(CreatVoucherWalletReqDto creatVoucherWalletReqDto) {
        Customer customer = customerRepository.findById(creatVoucherWalletReqDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(creatVoucherWalletReqDto.getCustomerId()));

        Voucher voucher = voucherRepository.findById(creatVoucherWalletReqDto.getVoucherId())
                .orElseThrow(() -> new VoucherNotFoundException(creatVoucherWalletReqDto.getVoucherId()));

        return voucherWalletRepository.create(new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId()));
    }

    @Transactional(readOnly = true)
    public List<Voucher> getAllocatedVouchers(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));

        return voucherRepository.findAllocatedVouchers(customer.getCustomerId());
    }
}
