package org.prgms.springbootbasic.service;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public void allocate(UUID customerId, UUID voucherId){
        walletRepository.allocateVoucherById(customerId, voucherId);
    }

    public void delete(UUID customerId, UUID voucherId){
        walletRepository.deleteVoucherById(customerId, voucherId);
    }

    public List<VoucherPolicy> searchVouchersFromCustomer(UUID customerId){
        return walletRepository.searchVouchersByCustomerId(customerId);
    }

    public List<Customer> searchCustomerFromVoucher(UUID voucherId){
        return walletRepository.searchCustomersByVoucherId(voucherId);
    }
}
