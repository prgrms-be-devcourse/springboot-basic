package com.pgms.part1.domain.wallet.service;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.voucher.dto.VoucherWalletResponseDtos;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.domain.wallet.repository.WalletRepository;
import com.pgms.part1.exception.ErrorCode;
import com.pgms.part1.exception.VoucherApplicationException;
import com.pgms.part1.util.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WalletService {
    private final Logger log = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;
    private final KeyGenerator keyGenerator;

    public WalletService(WalletRepository walletRepository, KeyGenerator keyGenerator) {
        this.walletRepository = walletRepository;
        this.keyGenerator = keyGenerator;
    }

    public void isVoucherAlreadyOwned(Long customer_id, Long voucher_id){
        walletRepository.findVouchersByCustomerId(customer_id).forEach(voucher -> {
            if(voucher.id().equals(voucher_id))
                throw new VoucherApplicationException(ErrorCode.VOUCHER_ALREADY_OWNED);
        });
    }

    public Wallet addWallet(WalletCreateRequestDto walletCreateRequestDto) {
        isVoucherAlreadyOwned(walletCreateRequestDto.userId(), walletCreateRequestDto.voucherId());
        Wallet wallet = new Wallet(keyGenerator.getKey(), walletCreateRequestDto.voucherId(), walletCreateRequestDto.userId());
        walletRepository.addWallet(wallet);
        log.info("customer {} wallet adds voucher {}", walletCreateRequestDto.userId(), walletCreateRequestDto.voucherId());
        return wallet;
    }

    public void deleteWallet(Long walletId) {
        walletRepository.deleteWallet(walletId);
        log.info("wallet {} is deleted", walletId);
    }

    @Transactional(readOnly = true)
    public List<VoucherWalletResponseDtos> listVouchersByCustomer(Long customerId) {
        List<VoucherWalletResponseDtos> vouchersByCustomerId = walletRepository.findVouchersByCustomerId(customerId);
        return vouchersByCustomerId;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> listCustomersByVoucher(Long voucherId) {
        List<Customer> customersByVoucherId = walletRepository.findCustomersByVoucherId(voucherId);
        List<CustomerResponseDto> customerResponseDtos = customersByVoucherId.stream().map(customer ->
                new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked())).toList();
        if(customersByVoucherId.size() == 0) throw new VoucherApplicationException(ErrorCode.CUSTOMER_NOT_EXIST);
        return customerResponseDtos;
    }
}
