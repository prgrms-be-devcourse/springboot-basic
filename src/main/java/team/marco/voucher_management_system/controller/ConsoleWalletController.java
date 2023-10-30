package team.marco.voucher_management_system.controller;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Controller;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.service.WalletService;
import team.marco.voucher_management_system.util.Console;

@Controller
public class ConsoleWalletController {
    private static final String INFO_DELIMINATOR = MessageFormat.format("\n{0}\n", "-".repeat(42));

    private final WalletService walletService;

    public ConsoleWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void supplyVoucher() {
        Console.print("쿠폰을 받을 고객 ID를 입력해주세요.");

        String customerId = Console.readString();
        walletService.checkCustomerId(customerId);

        Console.print("지급할 쿠폰 ID를 입력해주세요.");

        String voucherId = Console.readString();
        walletService.checkVoucherId(voucherId);

        if (walletService.supplyVoucher(customerId, voucherId) != 1) {
            Console.print("이미 지급된 쿠폰 ID 입니다.");

            return;
        }

        Console.print("쿠폰이 지급되었습니다.");
    }

    public void voucherList() {
        Console.print("보유 중인 쿠폰 목록을 확인할 고객 ID를 입력해주세요.");

        String customerId = Console.readString();

        List<String> voucherInfos = walletService.findVouchersByCustomerId(customerId)
                .stream()
                .map(Voucher::getInfo)
                .toList();

        Console.print(String.join(INFO_DELIMINATOR, voucherInfos));
        Console.print("조회가 완료되었습니다.");
    }

    public void returnVoucher() {
        Console.print("쿠폰을 반납할 고객 ID를 입력해주세요.");

        String customerId = Console.readString();
        walletService.checkCustomerId(customerId);

        Console.print("반납할 쿠폰 ID를 입력해주세요.");

        String voucherId = Console.readString();
        walletService.checkVoucherId(voucherId);

        if (walletService.returnVoucher(customerId, voucherId) != 1) {
            Console.print("등록된 쿠폰 ID가 아닙니다.");

            return;
        }

        Console.print("쿠폰이 반납되었습니다.");
    }

    public void customerList() {
        Console.print("보유 중인 고객 목록을 확인할 쿠폰 ID를 입력해주세요.");

        String voucherId = Console.readString();
        walletService.checkVoucherId(voucherId);

        List<String> customerInfos = walletService.findCustomersByVoucherId(voucherId)
                .stream()
                .map(Customer::getInfo)
                .toList();

        Console.print(String.join(INFO_DELIMINATOR, customerInfos));
        Console.print("조회가 완료되었습니다.");
    }
}
