package org.programmers.VoucherManagement.wallet.presentation;

import jakarta.validation.Valid;
import org.programmers.VoucherManagement.global.response.BaseResponse;
import org.programmers.VoucherManagement.wallet.application.WalletService;
import org.programmers.VoucherManagement.wallet.application.dto.WalletGetResponses;
import org.programmers.VoucherManagement.wallet.presentation.dto.WalletCreateRequestData;
import org.programmers.VoucherManagement.wallet.presentation.dto.WalletCreateResponseData;
import org.programmers.VoucherManagement.wallet.presentation.mapper.WalletControllerMapper;
import org.springframework.web.bind.annotation.*;

import static org.programmers.VoucherManagement.global.response.SuccessCode.DELETE_WALLET_SUCCESS;

@RestController
@RequestMapping("/wallets")
public class WalletRestController {
    private final WalletService walletService;

    public WalletRestController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping()
    public BaseResponse<WalletCreateResponseData> createWallet(@Valid @RequestBody WalletCreateRequestData data) {
        WalletCreateResponseData responseData =
                WalletControllerMapper.INSTANCE.createResponseToData(
                        walletService.createWallet(WalletControllerMapper.INSTANCE.dataToCreateRequest(data))
                );
        return new BaseResponse<>(responseData);
    }

    @GetMapping("/voucher/{voucherId}")
    public BaseResponse<WalletGetResponses> getWalletsByVoucherId(@PathVariable String voucherId) {
        WalletGetResponses responses = walletService.getWalletsByVoucherId(voucherId);
        return new BaseResponse<>(responses);
    }


    @GetMapping("/member/{memberId}")
    public BaseResponse<WalletGetResponses> getWalletsByMemberId(@PathVariable String memberId) {
        WalletGetResponses responses = walletService.getWalletsByMemberId(memberId);
        return new BaseResponse<>(responses);
    }

    @DeleteMapping("/{walletId}")
    public BaseResponse<String> deleteWallet(@PathVariable String walletId) {
        walletService.deleteWallet(walletId);

        return new BaseResponse<>(DELETE_WALLET_SUCCESS);
    }
}
