package com.prgrms.voucher_manager.wallet.service;

import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.service.VoucherService;
import com.prgrms.voucher_manager.wallet.Wallet;
import com.prgrms.voucher_manager.wallet.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WalletService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final WalletRepository walletRepository;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public WalletService(WalletRepository walletRepository, VoucherService voucherService, CustomerService customerService) {
        this.walletRepository = walletRepository;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

//    public static WalletService newWalletService(VoucherService voucherService, CustomerService customerService) {
//        return new WalletService(walletRepository, voucherService, customerService);
//    }

    public void findCustomerByVoucherType(String voucherType) {
        List<Voucher> findByType = voucherService.findByType(voucherType);
        List<UUID> walletByVoucherType = findWalletByVoucherType(findByType);
        customerService.findCustomerByWallet(walletByVoucherType);
    }

    public List<Wallet> findVoucherByCustomerId(UUID customerId) {
        List<Wallet> wallets = findAllWallet(customerId);
        AtomicInteger i = new AtomicInteger();
        wallets.forEach(e -> {
            System.out.print(i.getAndIncrement() + " : ");
            Voucher voucher = voucherService.findById(e.getVoucherId());
            System.out.println(voucher.toString());
        });
        return wallets;
    }


    private List<UUID> findWalletByVoucherType(List<Voucher> vouchers) {
        List<UUID> customerIds = new ArrayList<>();
        vouchers.forEach(v -> {
            List<Wallet> wallets = walletRepository.findByVoucherId(v.getVoucherID());
            wallets.forEach(w -> customerIds.add(w.getCustomerId()));
        });
        return customerIds;
    }

    public void deleteWallet(UUID customerId, int index) {
        List<Wallet> wallets = findAllWallet(customerId);
        UUID deleteVoucherId = wallets.get(index).getVoucherId();
        deleteVoucher(customerId, deleteVoucherId);
    }


    public void createWallet(UUID customerId, UUID voucherId) {
        Wallet createWallet = new Wallet(customerId, voucherId);
        walletRepository.insert(createWallet);
    }

    private List<Wallet> findAllWallet(UUID customerId) {
        List<Wallet> wallets = walletRepository.findByCustomerId(customerId);
        return wallets;
    }

    public void deleteVoucher(UUID customerId, UUID voucherId) {
        Wallet deleteWallet = new Wallet(customerId, voucherId);
        walletRepository.deleteByVoucherId(deleteWallet);
    }

}
