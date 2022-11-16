package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.AmountException;
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
    private static final String FIND_ALL_EXCEPTION = "파일에서 바우처 정보를 모두 읽어올 수 없습니다.";
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
        List<String> readVouchers = fileIO.read();
        readVouchers.forEach(readVoucher -> {
            try {
                voucherList.add(
                        fileParser.createVoucher(readVoucher));
            } catch (AmountException amountException) {
                logger.error("[숫자 변환 예외 발생] 예외 발생 바우처 -> {}", readVoucher, amountException);
            }
        });
        if(voucherList.size() != readVouchers.size()){
            throw new RuntimeException(FIND_ALL_EXCEPTION);
        }
        return voucherList;
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
