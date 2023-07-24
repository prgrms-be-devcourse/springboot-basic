package com.prgmrs.voucher.controller.console;

import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.dto.request.AssignVoucherRequest;
import com.prgmrs.voucher.dto.request.RemoveVoucherRequest;
import com.prgmrs.voucher.enums.StatusCode;
import com.prgmrs.voucher.exception.WrongRangeFormatException;
import com.prgmrs.voucher.service.WalletService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public ResponseDTO<?> assignVoucher(AssignVoucherRequest assignVoucherRequest) {
        try {
            return new ResponseDTO<>(walletService.assignVoucher(assignVoucherRequest), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }

    public ResponseDTO<?> removeVoucher(RemoveVoucherRequest removeVoucherRequest) {
        try {
            return new ResponseDTO<>(walletService.removeVoucher(removeVoucherRequest), StatusCode.REQUEST_OK);
        } catch (WrongRangeFormatException | DataAccessException e) {
            return new ResponseDTO<>(e.getMessage(), StatusCode.BAD_REQUEST);
        }
    }
}
