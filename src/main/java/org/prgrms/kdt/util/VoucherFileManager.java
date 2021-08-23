package org.prgrms.kdt.util;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VoucherFileManager implements FileManager {

    @Override
    public Map<UUID, Voucher> fileToMemory(String filePath) {
        var storage = new ConcurrentHashMap<UUID, Voucher>();

        try {
            for (String line : Files.readAllLines(Path.of(filePath))) {
                String[] params = line.split(",");

                // 파일에 적힌 3번째 파라미터인 문자열(바우처타입)을 Enum으로 변환후 비교한다.
                var voucherType = Enum.valueOf(VoucherType.class, params[2]);

                if (voucherType == VoucherType.FIXED) {
                    storage.put(
                            UUID.fromString(params[0]),
                            FixedAmountVoucher
                                    .builder()
                                    .voucherId(UUID.fromString(params[0]))
                                    .discount(Long.parseLong(params[1]))
                                    .build()
                    );
                }
                else if(voucherType == VoucherType.PERCENT) {
                    storage.put(
                            UUID.fromString(params[0]),
                            PercentDiscountVoucher
                                    .builder()
                                    .voucherId(UUID.fromString(params[0]))
                                    .discount(Long.parseLong(params[1]))
                                    .build()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return storage;
    }

    public void memoryToFile(String filePath, Map<UUID, Voucher> voucherMap) {
        try {
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file);
            for (Voucher voucher : voucherMap.values()){
                fileWriter.write(voucher.getVoucherId().toString());
                fileWriter.write(',');
                fileWriter.write(voucher.getDiscount().toString());
                fileWriter.write(',');
                fileWriter.write(voucher.getVoucherType().toString());
                fileWriter.write('\n');
            }
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
