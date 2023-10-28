package org.programmers.springboot.basic.domain.wallet.service;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.mapper.CustomerEntityMapper;
import org.programmers.springboot.basic.domain.customer.repository.CustomerRepository;
import org.programmers.springboot.basic.domain.customer.service.validate.EmailValidator;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherEntityMapper;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.entity.Wallet;
import org.programmers.springboot.basic.domain.wallet.exception.DuplicateWalletException;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletEntityMapper;
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
    private final CustomerRepository customerRepository;
    private final EmailValidator emailValidator;

    @Autowired
    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, CustomerRepository customerRepository, EmailValidator emailValidator) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
        this.emailValidator = emailValidator;
    }

    @Transactional
    public void createWallet(WalletRequestDto walletRequestDto) {

        Customer customer = findCustomerByEmail(walletRequestDto);
        Voucher voucher = findVoucherById(walletRequestDto);
        if (walletRepository.findByEmailAndId(customer.getEmail(), voucher.getVoucherId()).isPresent()) {
            log.warn("email {} and voucherId {} is already exists in Wallet",  customer.getEmail(), customer.getCustomerId());
            throw new DuplicateWalletException();
        }

        Wallet wallet = WalletEntityMapper.INSTANCE.mapToEntity(walletRequestDto);
        this.walletRepository.save(wallet);
    }

    public List<WalletResponseDto> walletListByEmail(WalletRequestDto walletRequestDto) {

        Customer customer = findCustomerByEmail(walletRequestDto);
        String email = customer.getEmail();

        emailValidator.validate(email);

        List<Wallet> wallets = this.walletRepository.findByEmail(email);

        return wallets.stream()
                .map(WalletEntityMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<VoucherResponseDto> voucherListFromWallet(List<WalletResponseDto> walletResponseDtos) {

        return walletResponseDtos.stream()
                .map(WalletResponseDto::getVoucherId)
                .map(voucherRepository::get)
                .map(voucher -> voucher.orElse(null))
                .map(VoucherEntityMapper.INSTANCE::entityToDto)
                .toList();
    }

    public List<WalletResponseDto> walletListByVoucherId(WalletRequestDto walletRequestDto) {

        Voucher voucher = findVoucherById(walletRequestDto);
        UUID voucherId = voucher.getVoucherId();
        List<Wallet> wallets = this.walletRepository.findById(voucherId);

        return wallets.stream()
                .map(WalletEntityMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public List<CustomerResponseDto> customerListFromWallet(List<WalletResponseDto> walletResponseDtos) {

        return walletResponseDtos.stream()
                .map(WalletResponseDto::getEmail)
                .map(customerRepository::findByEmail)
                .map(customer -> customer.orElse(null))
                .map(CustomerEntityMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public Customer findCustomerByEmail(WalletRequestDto walletRequestDto) {

        String email = walletRequestDto.getEmail();
        emailValidator.validate(email);

        return this.customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("No customer found for email: {}", email);
                    return new CustomerNotFoundException();
                });
    }

    public Voucher findVoucherById(WalletRequestDto walletRequestDto) {

        UUID voucherId = walletRequestDto.getVoucherId();
        return this.voucherRepository.get(voucherId)
                .orElseThrow(() -> {
                    log.warn("No voucher found for voucherId: {}", voucherId);
                    return new VoucherNotFoundException();
                });
    }

    @Transactional
    public void removeVoucherFromWallet(WalletRequestDto walletRequestDto) {

        Wallet wallet = WalletEntityMapper.INSTANCE.mapToEntity(walletRequestDto);
        this.walletRepository.delete(wallet);
    }
}
