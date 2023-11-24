package team.marco.voucher_management_system.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.facade.VoucherCustomerFacade;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.WalletRepository;

@Service
public class WalletService {
    private final WalletRepository walletRepository;
    private final VoucherCustomerFacade voucherCustomerFacade;

    public WalletService(WalletRepository walletRepository, VoucherCustomerFacade voucherCustomerFacade) {
        this.walletRepository = walletRepository;
        this.voucherCustomerFacade = voucherCustomerFacade;
    }

    public void checkVoucherId(UUID voucherId) {
        if (!voucherCustomerFacade.hasVoucher(voucherId)) {
            throw new NoSuchElementException("ID가 일치하는 쿠폰이 존재하지 않습니다.");
        }
    }

    public void checkCustomerId(UUID customerId) {
        if (!voucherCustomerFacade.hasCustomer(customerId)) {
            throw new NoSuchElementException("ID가 일치하는 고객이 존재하지 않습니다.");
        }
    }

    public int supplyVoucher(UUID customerId, UUID voucherId) {
        return walletRepository.link(customerId, voucherId);
    }

    public List<Voucher> findReceivedVouchers(UUID customerId) {
        List<UUID> voucherIds = walletRepository.getVoucherIds(customerId);

        return voucherCustomerFacade.getVouchers(voucherIds);
    }

    public int returnVoucher(UUID customerId, UUID voucherId) {
        return walletRepository.unlink(customerId, voucherId);
    }

    public List<Customer> findHavingCustomers(UUID voucherId) {
        List<UUID> customerIds = walletRepository.getCustomerIds(voucherId);

        return voucherCustomerFacade.getCustomers(customerIds);
    }
}
