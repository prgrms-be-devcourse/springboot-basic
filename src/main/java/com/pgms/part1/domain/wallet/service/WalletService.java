package com.pgms.part1.domain.wallet.service;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import com.pgms.part1.domain.wallet.entity.Wallet;
import com.pgms.part1.domain.wallet.repository.WalletRepository;
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

    public void addWallet(WalletCreateRequestDto walletCreateRequestDto) {
        try{
            Wallet wallet = new Wallet(keyGenerator.getKey(), walletCreateRequestDto.voucherId(), walletCreateRequestDto.userId());
            walletRepository.addWallet(wallet);
            log.info("customer {} wallet adds voucher {}", walletCreateRequestDto.userId(), walletCreateRequestDto.voucherId());
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void deleteWallet(Long walletId) {
        try{
            walletRepository.deleteWallet(walletId);
            log.info("wallet {} is deleted", walletId);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private static void isWalletExist(List<Wallet> walletByCustomerId) {
        if(walletByCustomerId.size() == 0)
            throw new RuntimeException("no voucher or customer for it");
    }

    @Transactional(readOnly = true)
    public List<VoucherResponseDto> listVouchersByCustomer(Long customerId) {
        List<Voucher> vouchersByCustomerId = walletRepository.findVouchersByCustomerId(customerId);
        List<VoucherResponseDto> voucherResponseDtos = vouchersByCustomerId.stream().map(voucher ->
                new VoucherResponseDto(voucher.getId(), voucher.getDiscount(), voucher.getVoucherDiscountType())).toList();
        if(voucherResponseDtos.size() == 0) throw new RuntimeException("no search result!!");
        return voucherResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDto> listCustomersByVoucher(Long voucherId) {
        List<Customer> customersByVoucherId = walletRepository.findCustomersByVoucherId(voucherId);
        List<CustomerResponseDto> customerResponseDtos = customersByVoucherId.stream().map(customer ->
                new CustomerResponseDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked())).toList();
        if(customersByVoucherId.size() == 0) throw new RuntimeException("no search result!!");
        return customerResponseDtos;
    }
}
