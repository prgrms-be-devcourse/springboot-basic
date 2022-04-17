package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.voucher.storage.VoucherStorage;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherStorage voucherStorage;

    public VoucherService(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    public void createFixedVoucher(int amount) {
        Voucher voucher;

        try {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
        } catch (IllegalArgumentException e) {
            logger.error("Fixed 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
            return;
        }

        voucherStorage.insert(voucher);
    }

    public void createPercentVoucher(int amount) {
        Voucher voucher;

        try {
            voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
        } catch (IllegalArgumentException e) {
            logger.error("Percent 입력 할인 금액은 범위에 맞지 않은 값을 입력하였습니다.");
            return;
        }

        voucherStorage.insert(voucher);
    }

    public void showVoucherList() {
        Map<UUID, Voucher> storage = voucherStorage.getStorage();
        storage.values().stream().forEach(System.out::println);
    }
}
