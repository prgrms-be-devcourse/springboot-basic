package team.marco.vouchermanagementsystem.controller;

import org.springframework.stereotype.Controller;
import team.marco.vouchermanagementsystem.service.WalletService;

@Controller
public class ConsoleWalletController {
    private final WalletService walletService;

    public ConsoleWalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    public void supplyVoucher() {

    }

    public void voucherList() {

    }

    public void returnVoucher() {

    }


    public void customerList() {

    }
}
