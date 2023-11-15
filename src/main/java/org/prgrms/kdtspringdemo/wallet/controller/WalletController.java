package org.prgrms.kdtspringdemo.wallet.controller;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.dto.WalletViewDto;
import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Controller
public class WalletController {
    private final WalletService walletService;
    private final OutputConsole outputConsole = new OutputConsole();
    private final InputConsole inputConsole = new InputConsole();
    private final Logger logger = LoggerFactory.getLogger(WalletController.class);

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void printVouchersByCustomerId() {
        try {
            outputConsole.getCustomerId();
            UUID customerId = UUID.fromString(inputConsole.getString());
            List<Voucher> vouchers = walletService.findVouchersById(customerId);
            vouchers.stream().forEach(voucher -> {
                VoucherViewDto voucherViewDto = new VoucherViewDto(voucher);
                outputConsole.printVoucher(voucherViewDto);
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void insertVoucherByCustomerId() {
        try {
            outputConsole.getWalletId();
            UUID walletId = UUID.fromString(inputConsole.getString());
            outputConsole.getCustomerId();
            UUID customerId = UUID.fromString(inputConsole.getString());
            outputConsole.getVoucherId();
            UUID voucherId = UUID.fromString(inputConsole.getString());
            walletService.addVoucherByCustomerId(walletId, customerId, voucherId);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteVoucherByVoucherId() {
        try {
            outputConsole.getCustomerId();
            UUID customerId = UUID.fromString(inputConsole.getString());
            outputConsole.getVoucherId();
            UUID voucherId = UUID.fromString(inputConsole.getString());
            walletService.deleteVoucherByVoucherId(customerId, voucherId);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void printAllWallet() {
        List<Wallet> walletList = walletService.findAll();
        walletList.stream().forEach(wallet -> {
            WalletViewDto walletViewDto = new WalletViewDto(wallet);
            outputConsole.printWallet(walletViewDto);
        });
    }

    public void endWalletMode() {
        outputConsole.printWalletModeEnd();
    }
}
