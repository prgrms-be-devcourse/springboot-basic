package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class Loader {
    private Loader() {
        throw new RuntimeException("생성 안돼!!");
    }

    public static Map<UUID, Voucher> loadFileToMemoryVoucher(String filePath) {
        Map<UUID, Voucher> vouchers = new LinkedHashMap<>();
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
        Map<UUID, Member> Members = new LinkedHashMap<>();
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
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (Map.Entry<UUID, Voucher> entry : memoryStorage.entrySet()) {
                writer.append(Converter.voucherToString(entry.getValue()));
            }
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }

    public static void saveMemoryMemberToFile(Map<UUID, Member> memoryStorage, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (Map.Entry<UUID, Member> entry : memoryStorage.entrySet()) {
                writer.append(Converter.memberToString(entry.getValue()));
            }
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }
}