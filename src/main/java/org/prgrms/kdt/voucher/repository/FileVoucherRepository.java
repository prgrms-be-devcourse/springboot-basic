package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.FileIOException;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private final static String path = "D:\\programmers\\w3\\2d\\w3-SpringBoot_Part_A\\src\\main\\resources\\VoucherRepository.txt";
    private final static String SPLIT_CODE = " ";
    private final static int TYPE_INDEX = 0;
    private final static int UUID_INDEX = 1;
    private final static int VALUE_INDEX = 2;
    private final File file;
    private final Map<UUID, Voucher> storage;


    public FileVoucherRepository() {
        this.storage = init();
        this.file = new File(path);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        insertFile(voucher);
        storage.put(voucher.voucherId(), voucher);
        return voucher;
    }

    private void insertFile(Voucher voucher) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new FileIOException(ErrorMessage.ERROR_OCCURRED_INPUTTING_FILE, e);
        }
    }

    @Override
    public Map<UUID, Voucher> findByAllVoucher() {
        return storage;
    }

    private Map<UUID, Voucher> init() {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new FileIOException(ErrorMessage.ERROR_READING_FILE, e);
        }
        Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
        for (String line : lines) {
            List<String> voucherData = List.of(line.split(SPLIT_CODE));
            VoucherType type = VoucherType.findByVoucherType(voucherData.get(TYPE_INDEX));
            UUID uuid = UUID.fromString(voucherData.get(UUID_INDEX));
            vouchers.put(uuid, newInstanceVoucher(type, uuid, voucherData.get(VALUE_INDEX)));
        }
        return vouchers;
    }

    /**
     * TODO
     * parseLong 때문에 FixedAmountVouncher에 데이터가 전달되지 못하고 Exception이 터져버립니다.
     * 개인적인 생각으로는 String을 보내줘서 Voucher들이 String에 대한 validate를 진행할 수 있는게 맞지 않나 싶습니다..
     */
    private Voucher newInstanceVoucher(VoucherType type, UUID uuid, String value) {
        if (type == VoucherType.FIXED) {
            return new FixedAmountVoucher(uuid, parseLong(value));
        }
        return new PercentDiscountVoucher(uuid, parseLong(value));
    }

    private long parseLong(String temp) {
        return Long.parseLong(temp);
    }
}