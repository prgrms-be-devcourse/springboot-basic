package com.prgms.VoucherApp.domain.wallet.model;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherDao;
import com.prgms.VoucherApp.domain.wallet.dto.WalletCreateRequest;
import com.prgms.VoucherApp.domain.wallet.dto.WalletResponse;
import com.prgms.VoucherApp.domain.wallet.dto.WalletsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final VoucherDao voucherDao;
    private final CustomerDao customerDao;
    private final WalletDao walletDao;

    public WalletService(VoucherDao voucherDao, CustomerDao customerDao, WalletDao walletDao) {
        this.voucherDao = voucherDao;
        this.customerDao = customerDao;
        this.walletDao = walletDao;
    }

    public WalletResponse save(WalletCreateRequest request) {
        Customer findCustomer = customerDao.findById(request.customerId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 customerId가 입력되었습니다."));

        Voucher findVoucher = voucherDao.findById(request.voucherId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 voucherId가 입력되었습니다."));

        Wallet wallet = new Wallet(UUID.randomUUID(), findCustomer, findVoucher);
        walletDao.save(wallet);

        return new WalletResponse(wallet);
    }

    public WalletResponse findOne(UUID walletId) {
        Wallet findWallet = walletDao.findById(walletId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 walletId가 입력되었습니다."));

        return new WalletResponse(findWallet);
    }

    public WalletsResponse findAllByCustomerId(UUID customerId) {
        List<WalletResponse> findAllWallets = walletDao.findByCustomerId(customerId)
            .stream()
            .map(WalletResponse::new)
            .toList();

        return new WalletsResponse(findAllWallets);
    }

    public WalletResponse findByVoucherId(UUID voucherId) {
        Wallet findWallet = walletDao.findByVoucherId(voucherId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 voucherId가 입력되었습니다."));

        return new WalletResponse(findWallet);
    }

    public void deleteById(UUID walletId) {
        walletDao.deleteById(walletId);
    }
}