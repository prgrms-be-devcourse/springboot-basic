package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class Loader {
    private Loader() {
        throw new RuntimeException("생성 안돼!!");
    }

    public static Map<UUID, Voucher> loadFileToMemoryVoucher(String filePath) {
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
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }

    public static Map<UUID, Member> loadFileToMemoryMember(String filePath) {
        Map<UUID, Member> Members = new ConcurrentHashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";

            while ((line = reader.readLine()) != null) {
                UUID curId = UUID.fromString(Converter.stringToArray(line, ",")[0]);
                Members.put(curId, Converter.stringToMember(line, MemberStatus.BLACK));
            }
            return Members;
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }

    public static void saveMemoryVoucherToFile(Map<UUID, Voucher> memoryStorage, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (Map.Entry<UUID, Voucher> entry : memoryStorage.entrySet()) {
                writer.append(Converter.voucherToString(entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }
}