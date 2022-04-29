package com.prgrms.vouchermanagement.wallet;

import com.prgrms.vouchermanagement.customer.Customer;
import com.prgrms.vouchermanagement.customer.CustomerService;
import com.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherWalletService {

    private final VoucherWalletRepository walletRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherWalletService(VoucherWalletRepository walletRepository, VoucherService voucherService, CustomerService customerService) {
        this.walletRepository = walletRepository;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public UUID addVoucherToWallet(UUID customerId, UUID voucherId) throws IllegalArgumentException, DataAccessException {
        validateVoucherId(voucherId);
        validateCustomerId(customerId);
        Wallet wallet = Wallet.of(UUID.randomUUID(), customerId, voucherId);
        walletRepository.save(wallet);
        return wallet.getWalletId();
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public void removeVoucherInWallet(UUID walletId) throws IllegalArgumentException, DataAccessException {
        validateWalletId(walletId);
        walletRepository.removeWallet(walletId);
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public List<Customer> findCustomerByVoucher(UUID voucherId) throws IllegalArgumentException, DataAccessException {
        validateVoucherId(voucherId);
        return walletRepository.findCustomerByVoucher(voucherId);
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    private void validateCustomerId(UUID customerId) throws IllegalArgumentException {
        if (!customerService.isRegisteredCustomer(customerId)) {
            throw new IllegalArgumentException("등록되지 않은 Customer입니다.");
        }
    }

    private void validateVoucherId(UUID voucherId) throws IllegalArgumentException {
        if (!voucherService.isRegisteredVoucher(voucherId)) {
            throw new IllegalArgumentException("등록되지 않은 Voucher입니다.");
        }
    }

    private void validateWalletId(UUID walletId) throws IllegalArgumentException {
        if (walletRepository.findWallet(walletId).isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 Wallet입니다.");
        }
    }
}
