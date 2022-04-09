package org.programmer.kdtspringboot;

import org.programmer.kdtspringboot.voucher.Voucher;
import org.programmer.kdtspringboot.voucher.VoucherService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class VoucherSystem {

    private final Console console;
    private final VoucherService voucherService;

    public VoucherSystem(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {

        while (true) {
            console.menu();
            String inputString = console.input("선택 :").toLowerCase();
            switch (inputString) {
                case "exit":
                    console.exit();
                    return;
                case "list":
                    showVoucherList();
                    break;
                case "create":
                    createVoucher();
                    break;
                default:
                    console.print("제대로 입력해주세요");
            }
        }
    }

    private void showVoucherList() {
        Map<UUID, Voucher> map = voucherService.findAllVouchers();
        if (!map.isEmpty()) {
            for (UUID id : map.keySet()) {
                console.print(map.get(id).getVoucherId() + "," + map.get(id).getValue());
            }
        }
    }

    private void createVoucher() {
        console.choice();
        String inputString = console.input("선택 :").toLowerCase();
        int value = Integer.parseInt(console.input("할인값 :"));
        if (inputString.equals("amount")) {
            voucherService.createFixedAmountVoucher(UUID.randomUUID(), value);
        } else if (inputString.equals("percent")) {
            voucherService.createPercentDiscountVoucher(UUID.randomUUID(), value);
        } else {
            console.print("잘못입력하셨습니다. 처음부터 다시 해주세요");
        }
    }

}
