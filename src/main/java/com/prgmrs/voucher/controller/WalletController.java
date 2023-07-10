package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.WalletService;
import org.springframework.stereotype.Component;

@Component
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public WalletResponse assignVoucher(WalletRequest walletRequest) throws WrongRangeFormatException {
        return walletService.assignVoucher(walletRequest);
    }

    public WalletResponse freeVoucher(WalletRequest walletRequest) throws WrongRangeFormatException {
        return walletService.freeVoucher(walletRequest);
    }
}
