package org.prgrms.devcourse.util;

import org.prgrms.devcourse.domain.FixedAmountVoucher;
import org.prgrms.devcourse.domain.PercentDiscountVoucher;
import org.prgrms.devcourse.domain.Voucher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VoucherFileReader implements FileReader {
    @Override
    public Map<UUID, Voucher> readFile(String filePath) {
        var voucherMap = new ConcurrentHashMap<UUID, Voucher>();

        try {
            for (String line : Files.readAllLines(Path.of(filePath))) {
                String[] params = line.split(",");
                if (params[0].equals("FixedAmountVoucher")) {
                    voucherMap.put(UUID.fromString(params[1])
                            , FixedAmountVoucher.of(UUID.fromString(params[1]), Long.parseLong(params[2])));
                } else {
                    voucherMap.put(UUID.fromString(params[1])
                            , PercentDiscountVoucher.of(UUID.fromString(params[1]), Long.parseLong(params[2])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return voucherMap;
    }
}
