package team.marco.voucher_management_system.controller;

import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.service.WalletService;
import team.marco.voucher_management_system.util.Console;

@Controller
public class ConsoleWalletController {
    private final WalletService walletService;

    public ConsoleWalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    public void supplyVoucher() {
        Console.print("쿠폰을 받을 고객 ID를 입력해주세요.");

        String customerId = Console.readString();

        Console.print("지급할 쿠폰 ID를 입력해주세요.");

        String voucherId = Console.readString();
    }

    public void voucherList() {
        Console.print("보유 중인 쿠폰 목록을 확인할 고객 ID를 입력해주세요.");

        String customerId = Console.readString();
    }

    public void returnVoucher() {
        Console.print("쿠폰을 반납할 고객 ID를 입력해주세요.");

        String customerId = Console.readString();

        Console.print("반납할 쿠폰 ID를 입력해주세요.");

        String voucherId = Console.readString();
    }


    public void customerList() {
        Console.print("보유 중인 고객 목록을 확인할 쿠폰 ID를 입력해주세요.");

        String voucherId = Console.readString();
    }
}
