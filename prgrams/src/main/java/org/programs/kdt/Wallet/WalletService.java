package org.programs.kdt.Wallet;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerService;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.*;


@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;

    private final CustomerService customerService;

    private final VoucherService voucherService;


    public Wallet testCreate(UUID customerId, UUID voucherId, UUID walletId) {
        Voucher retrieveVoucher = voucherService.findById(voucherId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_VOUCHER_ID));
        Customer retrieveCustomer = customerService.findById(customerId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CUSTOMER_ID));

        Wallet wallet = new Wallet(retrieveVoucher, retrieveCustomer, walletId, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        checkDuplicationWalletId(wallet.getWalletId());

        walletRepository.insert(wallet);
        return wallet;
    }

    public Wallet create(UUID customerId, UUID voucherId) {
        Voucher retrieveVoucher = voucherService.findById(voucherId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_VOUCHER_ID));
        Customer retrieveCustomer = customerService.findById(customerId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CUSTOMER_ID));
        Wallet wallet = new Wallet(retrieveVoucher, retrieveCustomer, UUID.randomUUID(), LocalDateTime.now());
        checkDuplicationWalletId(wallet.getWalletId());

        walletRepository.insert(wallet);
        return wallet;
    }

    private Optional<Customer> checkFoundCustomerId(UUID customerId) {
        boolean isId = customerService.existId(customerId);
        if (!isId) {
            throw new EntityNotFoundException(NOT_FOUND_CUSTOMER_ID);
        }
        return customerService.findById(customerId);
    }



    public List <Wallet> findAll() {
        return walletRepository.findAll();
    }

    public void delete(UUID walletId) {
        walletRepository.deleteById(walletId);
    }

    public List<Wallet> findByVoucherId(UUID voucherId) {
        return walletRepository.findByVoucherId(voucherId);
    }
    public List<Wallet> findByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId);
    }

    public Wallet findByWalletId(UUID walletId) {
        checkFoundWalletId(walletId);
        return walletRepository.findById(walletId).get();
    }

    public List<Wallet> findByCustomerEmail(String email) {
        return walletRepository.findByCustomerEmail(email);
    }
    public void deleteAll() {
        walletRepository.deleteAll();
    }


    private void checkFoundWalletId(UUID walletId) {
        boolean isId = walletRepository.existWalletId(walletId);
        if (!isId) {
            throw new EntityNotFoundException(DUPLICATION_WALLET_ID);
        }
    }

    private void checkDuplicationWalletId(UUID walletId) {
        boolean isId = walletRepository.existWalletId(walletId);
        if (isId) {
            throw new DuplicationException(DUPLICATION_WALLET_ID);
        }
    }


    public void deleteByCustomerId(UUID customerId) {
        checkFoundCustomerId(customerId);
        walletRepository.deleteByCustomerId(customerId);
    }

    public void deleteById(UUID walletId) {
        checkFoundWalletId(walletId);
        walletRepository.deleteById(walletId);
    }
}
