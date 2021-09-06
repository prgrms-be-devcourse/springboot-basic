package org.prgrms.kdt.voucher.util;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoucherFileReader {
    public Map<UUID, Voucher> readFile(String path) {
        var map = new ConcurrentHashMap<UUID, Voucher>();

        try {
            for (String line : Files.readAllLines(Path.of(path))) {
                String[] params = line.split(",");

                UUID uuid = UUID.fromString(params[0]);
                String discountType = params[1];
                long amount = Long.parseLong(params[2]);

                switch (VoucherType.getVoucherType(discountType)) {
                    case FIXED_AMOUNT_VOUCHER -> map.put(uuid , new FixedAmountVoucher(uuid, amount));
                    case PERCENT_DISCOUNT_VOUCHER -> map.put(uuid , new PercentDiscountVoucher(uuid, amount));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
