package org.prgrms.kdt.storage;

import org.prgrms.kdt.io.FileIO;
import org.prgrms.kdt.utils.FileParser;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Profile("prod")
public class FileVoucherStorage implements VoucherStorage {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherStorage.class);
    private final FileIO fileIO;
    private final FileParser fileParser;

    public FileVoucherStorage(FileIO fileIO, FileParser fileParser) {
        this.fileIO = fileIO;
        this.fileParser = fileParser;
    }

    @Override
    public void save(Voucher voucher) {
        fileIO.write(
                fileParser.getVoucherInfo(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> voucherList = new ArrayList<>();
        try {
            List<String> readVouchers = fileIO.read();
            readVouchers.forEach(voucher ->
                    voucherList.add(
                            fileParser.createVoucher(voucher)));
            return voucherList;
        } catch (RuntimeException e) {
            logger.error("파일에 저장된 바우처 정보가 없습니다. 빈 배열을 반환합니다.");
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<String> readVouchers = fileIO.read();
        Map<UUID, Voucher> voucherMap = new HashMap<>();
        readVouchers.forEach(voucherLine -> {
            Voucher newVoucher = fileParser.createVoucher(voucherLine);
            voucherMap.put(newVoucher.getVoucherId(), newVoucher);
        });
        return Optional.ofNullable(voucherMap.get(voucherId));
    }
}
