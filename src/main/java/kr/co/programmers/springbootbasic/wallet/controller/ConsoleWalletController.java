package kr.co.programmers.springbootbasic.wallet.controller;

import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import kr.co.programmers.springbootbasic.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@Profile("console")
public class ConsoleWalletController {
    private final WalletService walletService;

    public ConsoleWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public WalletSaveDto saveVoucherInWallet(WalletSaveDto saveRequest) {
        return walletService.saveVoucherInCustomerWallet(saveRequest);
    }

    public WalletResponse findWalletById(String walletId) {
        List<VoucherResponse> voucherDtos = walletService.findWalletById(walletId);
        UUID walletUUID = ApplicationUtils.toUUID(walletId.getBytes());

        return new WalletResponse(walletUUID, voucherDtos);
    }
}
