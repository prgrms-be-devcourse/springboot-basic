package org.prgrms.kdt.util;

import org.prgrms.kdt.exception.DatabaseException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.Voucher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class Loader {
    private Loader() {
        throw new RuntimeException("생성 안돼!!");
    }

    public static List<Voucher> loadFileToMemoryVoucher(String filePath) {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";

            while ((line = reader.readLine()) != null) {
                vouchers.add(Converter.stringToVoucher(line));
            }
            return vouchers;
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }

    public static List<Member> loadFileToMemoryMember(String filePath) {
        List<Member> Members = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";

            while ((line = reader.readLine()) != null) {
                Members.add(Converter.stringToMember(line, MemberStatus.BLACK));
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

    public static void saveMemoryMemberToFile(Map<UUID, Member> memoryStorage, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            for (Map.Entry<UUID, Member> entry : memoryStorage.entrySet()) {
                writer.append(Converter.memberToString(entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            throw new DatabaseException(ErrorMessage.FILE_ACCESS_ERROR, e);
        }
    }
}