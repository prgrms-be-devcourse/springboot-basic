package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    private final VoucherJdbcRepository voucherRepository;

    private final CustomerRepository customerRepository;

    public WalletServiceImpl(WalletRepository walletRepository, VoucherJdbcRepository voucherRepository, CustomerRepository customerRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public int insert(UUID walletId, Voucher voucher) {
        var rows = voucherRepository.insertWalletIdToVoucher(walletId, voucher);
        if (rows == 0) {
            throw new RuntimeException("nothing was updated");
        }
        return rows;
    }

    @Override
    public int insertWalletByCustomerId(UUID customerId) {
        return walletRepository.insert(UUID.randomUUID(), customerId);
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        var wallets = walletRepository.findByCustomerId(customerId);
        var walletIds = wallets.stream()
                .map(v -> v.getWalletId().toString().getBytes())
                .collect(Collectors.toList());
        return walletRepository.findVouchersByWalletList(walletIds);
    }

    public List<Wallet> finsWalletsByCustomerId(UUID customerId) {
        return walletRepository.findByCustomerId(customerId);
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        var wallets = walletRepository.findByCustomerId(customerId);

        wallets.forEach(wallet -> voucherRepository.deleteByWalletId(wallet.getWalletId()));
    }

    @Override
    public List<Customer> findCustomersByVoucherType(VoucherType voucherType) {
        var vouchers = voucherRepository.findByVoucherType(voucherType);
        var walletIdList = vouchers.stream()
                .map(v -> v.getWalletId().toString().getBytes())
                .collect(Collectors.toList());
        return customerRepository.findCustomersByWalletIds(walletIdList);
    }

}
