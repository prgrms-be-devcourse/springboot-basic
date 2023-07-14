package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.dto.ResponseDTO;
import com.prgmrs.voucher.dto.request.WalletRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.enums.ErrorCode;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.WalletService;
import org.springframework.stereotype.Component;

@Component
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public ResponseDTO<WalletResponse> assignVoucher(WalletRequest walletRequest)  {
        try {
            return new ResponseDTO<WalletResponse>(walletService.assignVoucher(walletRequest), ErrorCode.REQUEST_SUCCESS);
        } catch (WrongRangeFormatException e) {
            return new ResponseDTO<WalletResponse>(walletService.assignVoucher(walletRequest), ErrorCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<WalletResponse> removeVoucher(WalletRequest walletRequest) {
        try {
            return new ResponseDTO<WalletResponse>(walletService.removeVoucher(walletRequest), ErrorCode.REQUEST_SUCCESS);
        } catch (WrongRangeFormatException e) {
            return new ResponseDTO<WalletResponse>(walletService.removeVoucher(walletRequest), ErrorCode.BAD_REQUEST);
        }
    }
}
