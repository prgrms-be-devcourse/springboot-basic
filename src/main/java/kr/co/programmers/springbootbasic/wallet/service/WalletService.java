package kr.co.programmers.springbootbasic.wallet.service;

import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import kr.co.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile("web")
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public WalletSaveDto saveVoucherInCustomerWallet(WalletSaveDto saveRequest) {
        String voucherId = saveRequest.getVoucherId();
        String walletId = saveRequest.getWalletId();
        repository.saveVoucherInCustomerWallet(voucherId, walletId);

        return saveRequest;
    }

    public List<VoucherResponse> findWalletById(String walletId) {
        Wallet wallet = repository.findAllVouchersById(walletId);

        return makeVoucherResponseList(wallet);
    }

    private static List<VoucherResponse> makeVoucherResponseList(Wallet wallet) {
        List<Voucher> vouchers = wallet.getVouchers();

        return vouchers.stream()
                .map(VoucherResponse::convertToVoucherResponse)
                .toList();
    }
}
