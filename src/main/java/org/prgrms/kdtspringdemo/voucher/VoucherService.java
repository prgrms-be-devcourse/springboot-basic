package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.console.Input;
import org.prgrms.kdtspringdemo.voucher.storage.VoucherStorage;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.Voucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherStorage voucherStorage;

    @Autowired
    public VoucherService(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

    // Voucher 생성 로직(타입과 할인금액을 입력받는다)
    public void createVoucher(VoucherType voucherType, int amount) {

        switch (voucherType) {
            case FIXED -> {
                Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), amount);
                voucherStorage.insert(voucher);
            }
            case PERCENT -> {
                Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), amount);
                voucherStorage.insert(voucher);
            }
        }
    }


    // List 작성 시 모든 데이터를 모여주는 로직
    public void showAllVoucher() {
        Map<UUID, Voucher> storage = voucherStorage.getStorage();
        storage.entrySet().stream().forEach(s -> logger.info(
                "\nid : " + s.getKey() +
                        "\nVoucher type : " + s.getValue().getAmount() +
                        "\nDiscount amount : " + s.getValue().getAmount()));
    }
}
