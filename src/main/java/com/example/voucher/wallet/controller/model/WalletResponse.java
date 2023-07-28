package com.example.voucher.wallet.controller.model;

import java.util.Arrays;
import java.util.List;
import com.example.voucher.constant.ResponseStatus;
import com.example.voucher.wallet.service.dto.WalletDTO;

public class WalletResponse {

    private ResponseStatus status;
    private String errorMsg;
    private List<WalletDTO> wallets;

    public WalletResponse(ResponseStatus status) {
        this.status = status;
    }

    public WalletResponse(ResponseStatus status, List<WalletDTO> wallets) {
        this.status = status;
        this.wallets = wallets;
    }

    public WalletResponse(ResponseStatus status, WalletDTO wallets) {
        this.status = status;
        this.wallets = Arrays.asList(wallets);
    }

    public WalletResponse(ResponseStatus status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public List<WalletDTO> getWallets() {
        return wallets;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
