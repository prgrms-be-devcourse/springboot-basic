package org.prgrms.kdt.voucher.util;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoucherFileReader {
    public Map<UUID, Voucher> readFile(String path) throws IOException {
        var map = new ConcurrentHashMap<UUID, Voucher>();

        for (String line : Files.readAllLines(Path.of(path))) {
            String[] params = line.split(",");
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            UUID uuid = UUID.fromString(params[0]);
            VoucherType discountType = VoucherType.valueOf(params[1]);
            long amount = Long.parseLong(params[2]);
            LocalDateTime date = LocalDateTime.parse(params[3], formatter);

            voucherGenerate(map, uuid, discountType, amount, date);
        }
        return map;
    }

    private void voucherGenerate(ConcurrentHashMap<UUID, Voucher> map, UUID uuid, VoucherType voucherType, long amount, LocalDateTime date) {
        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> map.put(uuid, new FixedAmountVoucher(uuid, amount, voucherType, date));
            case PERCENT_DISCOUNT_VOUCHER -> map.put(uuid, new PercentDiscountVoucher(uuid, amount, voucherType, date));
            default -> throw new IllegalArgumentException("해당 타입에 맞는 바우처 없음");
        }
    }
}
