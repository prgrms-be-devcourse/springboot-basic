package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.voucher.storage.VoucherStorage;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.Voucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherStorage voucherStorage;

    @Autowired
    public VoucherService(VoucherStorage voucherStorage) {
        this.voucherStorage = voucherStorage;
    }

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


    public void showAllVoucher() {
        Map<UUID, Voucher> storage = voucherStorage.getStorage();
        storage.entrySet().stream().forEach(s -> System.out.println(
                        "id : " + s.getKey() +
                        "\nVoucher type : " + s.getValue().getAmount() +
                        "\nDiscount amount : " + s.getValue().getAmount()));
    }
}
