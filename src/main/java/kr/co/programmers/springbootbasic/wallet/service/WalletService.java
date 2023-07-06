package kr.co.programmers.springbootbasic.wallet.service;

import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import kr.co.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public WalletSaveDto saveVoucherInCustomerWallet(WalletSaveDto saveRequest) {
        UUID voucherId = saveRequest.getVoucherId();
        UUID walletId = saveRequest.getWalletId();
        repository.saveVoucherInCustomerWallet(voucherId, walletId);

        return saveRequest;
    }

    public WalletResponse findWalletById(UUID walletId) {
        Wallet wallet = repository.findAllVouchersById(walletId);
        List<VoucherResponse> voucherDtos = makeVoucherResponseList(wallet);

        return new WalletResponse(walletId, voucherDtos);
    }

    private static List<VoucherResponse> makeVoucherResponseList(Wallet wallet) {
        List<VoucherResponse> voucherDtos = new ArrayList<>();
        for (Voucher voucher : wallet.getVouchers()) {
            var dto = ApplicationUtils.convertToVoucherResponse(voucher);
            voucherDtos.add(dto);
        }

        return voucherDtos;
    }
}
