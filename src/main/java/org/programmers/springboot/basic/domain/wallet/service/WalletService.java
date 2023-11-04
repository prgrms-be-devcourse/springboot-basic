package org.programmers.springboot.basic.domain.wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerMapper;
import org.programmers.springboot.basic.domain.customer.repository.JdbcCustomerRepository;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;
import org.programmers.springboot.basic.domain.wallet.exception.DuplicateWalletException;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletMapper;
import org.programmers.springboot.basic.domain.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final JdbcCustomerRepository customerRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, JdbcCustomerRepository customerRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void createWallet(WalletRequestDto walletRequestDto) {

        Customer customer = findCustomerByEmail(walletRequestDto);
        Voucher voucher = findVoucherById(walletRequestDto);
        if (isDuplicate(customer, voucher)) {
            log.warn("email {} and voucherId {} is already exists in Wallet",
                    customer.getEmail().getEmail(), customer.getCustomerId());
            throw new DuplicateWalletException();
        }

        Wallet wallet = WalletMapper.INSTANCE.mapToEntity(walletRequestDto);
        this.walletRepository.save(wallet);
    }

    private boolean isDuplicate(Customer customer, Voucher voucher) {
        return walletRepository.findByEmailNId(customer.getEmail(), voucher.getVoucherId()).isPresent();
    }

    public List<WalletResponseDto> walletListByEmail(WalletRequestDto walletRequestDto) {

        Customer customer = findCustomerByEmail(walletRequestDto);
        Email email = customer.getEmail();

        email.validate();

        List<Wallet> wallets = this.walletRepository.findByEmail(email);
        return wallets.stream()
                .map(WalletMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<VoucherResponseDto> voucherListFromWallet(List<WalletResponseDto> walletResponseDtos) {

        return walletResponseDtos.stream()
                .map(WalletResponseDto::getVoucherId)
                .map(voucherRepository::findById)
                .map(voucher -> voucher.orElse(null))
                .map(VoucherMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<WalletResponseDto> walletListByVoucherId(WalletRequestDto walletRequestDto) {

        Voucher voucher = findVoucherById(walletRequestDto);
        UUID voucherId = voucher.getVoucherId();
        List<Wallet> wallets = this.walletRepository.findById(voucherId);

        return wallets.stream()
                .map(WalletMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<CustomerResponseDto> customerListFromWallet(List<WalletResponseDto> walletResponseDtos) {

        return walletResponseDtos.stream()
                .map(WalletResponseDto::getEmail)
                .map(customerRepository::findByEmail)
                .map(customer -> customer.orElse(null))
                .map(CustomerMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public Customer findCustomerByEmail(WalletRequestDto walletRequestDto) {

        Email email = walletRequestDto.getEmail();
        email.validate();

        return this.customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("No customer found for email: {}", email);
                    return new CustomerNotFoundException();
                });
    }

    public Voucher findVoucherById(WalletRequestDto walletRequestDto) {

        UUID voucherId = walletRequestDto.getVoucherId();
        return this.voucherRepository.findById(voucherId)
                .orElseThrow(() -> {
                    log.warn("No voucher found for voucherId: {}", voucherId);
                    return new VoucherNotFoundException();
                });
    }

    @Transactional
    public void removeVoucherFromWallet(WalletRequestDto walletRequestDto) {

        Wallet wallet = WalletMapper.INSTANCE.mapToEntity(walletRequestDto);
        this.walletRepository.delete(wallet);
    }
}
