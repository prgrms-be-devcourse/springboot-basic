package com.programmers.vouchermanagement.wallet.application;

import com.programmers.vouchermanagement.customer.dto.CustomerResponseDto;
import com.programmers.vouchermanagement.customer.exception.CustomerNotFoundException;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;
import com.programmers.vouchermanagement.wallet.domain.Wallet;
import com.programmers.vouchermanagement.wallet.dto.WalletRequestDto;
import com.programmers.vouchermanagement.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public WalletService(WalletRepository walletRepository, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.walletRepository = walletRepository;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public void createWallet(WalletRequestDto walletRequestDto) {

        UUID customerId = walletRequestDto.getCustomerId();
        UUID voucherId = walletRequestDto.getVoucherId();

        customerRepository.findById(customerId)
                        .orElseThrow(CustomerNotFoundException::new);

        voucherRepository.findById(voucherId)
                        .orElseThrow(VoucherNotFoundException::new);

        walletRepository.save(new Wallet(UUID.randomUUID(), customerId, voucherId));
    }

    public List<VoucherResponseDto> readVouchersByCustomer(UUID customerId) {

        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);

        List<VoucherResponseDto> voucherResponseDtos = wallets.stream()
                .map(Wallet::getVoucherId)
                .map(voucherRepository::findById)
                .map(optionalVoucher -> optionalVoucher.orElseThrow(VoucherNotFoundException::new))
                .map(voucher -> new VoucherResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount()))
                .toList();

        return voucherResponseDtos;
    }

    public List<CustomerResponseDto> readCustomersByVoucher(UUID voucherId) {

        List<Wallet> wallets = walletRepository.findByVoucherId(voucherId);

        List<CustomerResponseDto> customerResponseDtos = wallets.stream()
                .map(Wallet::getCustomerId)
                .map(customerRepository::findById)
                .map(optionalCustomer -> optionalCustomer.orElseThrow(CustomerNotFoundException::new))
                .map(customer -> new CustomerResponseDto(customer.getCustomerId(), customer.getName(), customer.getCustomerType()))
                .toList();

        return customerResponseDtos;
    }

    public void removeWalletsByCustomer(UUID customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        walletRepository.deleteByCustomerId(customerId);
    }
}
