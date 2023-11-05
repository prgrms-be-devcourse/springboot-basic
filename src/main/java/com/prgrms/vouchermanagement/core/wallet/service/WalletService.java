package com.prgrms.vouchermanagement.core.wallet.service;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;
import com.prgrms.vouchermanagement.core.customer.dto.CustomerDto;
import com.prgrms.vouchermanagement.core.customer.repository.CustomerRepository;
import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import com.prgrms.vouchermanagement.core.wallet.domain.Wallet;
import com.prgrms.vouchermanagement.core.wallet.dto.WalletDto;
import com.prgrms.vouchermanagement.core.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;
    private final WalletRepository walletRepository;

    public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository, WalletRepository walletRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.walletRepository = walletRepository;
    }


    /**
     * 기능 1: 특정 고객에게 특정 바우처를 할당
     */
    public WalletDto assignVoucher(String customerId, String voucherId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("고객이 존재하지 않습니다.");
        }
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        if (optionalVoucher.isEmpty()) {
            throw new IllegalArgumentException("바우처가 존재하지 않습니다.");
        }
        Wallet savedWallet = walletRepository.save(new Wallet(optionalCustomer.get().getId(), optionalVoucher.get().getId()));
        return new WalletDto(savedWallet.getId(), savedWallet.getCustomerId(), savedWallet.getVoucherId());
    }


    /**
     * 기능 2: 특정 고객이 보유한 바우처들 조회
     */
    public List<VoucherDto> findVouchersByCustomer(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("고객이 존재하지 않습니다.");
        }

        List<Wallet> walletList = walletRepository.findByCustomerId(optionalCustomer.get().getId());
        List<String> voucherIdList = walletList.stream()
                .map(Wallet::getVoucherId)
                .collect(Collectors.toList());

        List<Voucher> voucherList = voucherRepository.findAllByIds(voucherIdList);
        return voucherList.stream()
                .map(it -> new VoucherDto(it.getId(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
    }


    /**
     * 기능 3: 특정 고객이 보유한 특정 바우처 삭제
     *
     */
    public void deleteVouchersByCustomer(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("고객이 존재하지 않습니다.");
        }
        walletRepository.deleteAllByCustomerId(customerId);
    }


    /**
     * 기능 4: 특정 바우처를 보유한 고객들 조회
     *
     */
    public List<CustomerDto> findCustomersByVoucher(String voucherId) {
        Optional<Voucher> optionalVoucher = voucherRepository.findById(voucherId);
        if (optionalVoucher.isEmpty()) {
            throw new IllegalArgumentException("바우처가 존재하지 않습니다.");
        }

        List<Wallet> walletList = walletRepository.findByVoucherId(optionalVoucher.get().getId());
        List<String> customerIdList = walletList.stream()
                .map(Wallet::getCustomerId)
                .collect(Collectors.toList());

        List<Customer> customerList = customerRepository.findAllByIds(customerIdList);
        return customerList.stream()
                .map(it -> new CustomerDto(it.getId(), it.getName(), it.getEmail()))
                .collect(Collectors.toList());
    }
}
