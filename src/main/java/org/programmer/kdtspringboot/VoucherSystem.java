package org.programmer.kdtspringboot;

import org.programmer.kdtspringboot.voucher.Voucher;
import org.programmer.kdtspringboot.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class VoucherSystem {

    private final Console console;
    private final VoucherService voucherService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherSystem.class);

    public VoucherSystem(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run() {

        while (true) {
            console.menu();
            String inputString = console.input("선택: ").toLowerCase();
            switch (inputString) {
                case "exit":
                    logger.info("exit 입력");
                    console.exit();
                    return;
                case "list":
                    logger.info("list 입력");
                    showVoucherList();
                    break;
                case "create":
                    logger.info("create 입력");
                    createVoucher();
                    break;
                default:
                    logger.warn("잘못 입력");
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

        String inputString = console.input("선택: ").toLowerCase();
        logger.info("Voucher 종류 선택: " + inputString);
        String discountString = console.input("할인값: ");
        if (!isNumber(discountString)) {
            console.print("할인값을 잘못 입력하셨습니다");
            logger.warn("할인값 잘못 입력");
            return;
        }
        int discount = Integer.parseInt(discountString);
        logger.info("할인값 입력: " + discountString);

        Voucher voucher;
        if (inputString.equals("amount")) {
            voucher = voucherService.createFixedAmountVoucher(UUID.randomUUID(), discount);
        } else if (inputString.equals("percent")) {
            voucher = voucherService.createPercentDiscountVoucher(UUID.randomUUID(), discount);
        } else {
            console.print("잘못입력하셨습니다. 처음부터 다시 해주세요");
            return;
        }
        logger.info("voucher 생성(" + voucher.getVoucherId() + "," + voucher.getValue() + ")");
    }

    private boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
