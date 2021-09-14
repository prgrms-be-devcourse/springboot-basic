package org.prgrms.kdt.wallet;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService{

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
        if(rows == 0){
            throw new RuntimeException("nothing was updated");
        }
        return rows;
    }

    @Override
    public List<Voucher> findCustomerVoucher(UUID customerId) {
        var wallets = walletRepository.findByCustomerId(customerId);
        // customerId 로 walletList 를 가져온다음 해당 지갑안에 들은 모든 바우처를 연결해서 내보내준다.
        return wallets.stream()
                .map(wallet -> voucherRepository.findByWalletId(wallet.getWalletId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        var wallets = walletRepository.findByCustomerId(customerId);

        wallets.forEach(wallet -> voucherRepository.deleteByWalletId(wallet.getWalletId()));
    }

    @Override
    public List<Customer> findByVoucher(VoucherType voucherType) {
        List<Customer> retValue = new ArrayList<>();

        var vouchers = voucherRepository.findByVoucherType(voucherType);
        vouchers.stream()
                .map(voucher -> walletRepository.findByWalletId(voucher.getWalletId()))
                .forEach(wallets -> wallets.stream()
                        .map(wallet -> customerRepository.findById(wallet.getCustomerId()))
                        .forEach(customer -> customer.ifPresent(retValue::add)));
        return retValue;

    }
}
