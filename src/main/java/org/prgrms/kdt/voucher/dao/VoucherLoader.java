package org.prgrms.kdt.voucher.dao;

import org.prgrms.kdt.exception.FileAccessException;
import org.prgrms.kdt.util.Converter;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VoucherLoader {
    private final String filePath;

    public VoucherLoader(@Value("${filePath.voucher}") String filePath) {
        this.filePath = filePath;
    }

    public Map<UUID, Voucher> loadFileToMemoryVoucher() {
        Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";

            while ((line = reader.readLine()) != null) {
                UUID curId = UUID.fromString(Converter.stringToArray(line, ",")[0]);
                vouchers.put(curId, Converter.stringToVoucher(line));
            }
            return vouchers;
        } catch (IOException e) {
            throw new FileAccessException("파일에 접근하다 문제가 생겼습니다.", e);
        }
    }

    public void saveMemoryVoucherToFile(Map<UUID, Voucher> memoryStorage) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (Map.Entry<UUID, Voucher> entry : memoryStorage.entrySet()) {
                writer.append(Converter.voucherToString(entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            throw new FileAccessException("파일에 접근하다 문제가 생겼습니다.", e);
        }
    }
}