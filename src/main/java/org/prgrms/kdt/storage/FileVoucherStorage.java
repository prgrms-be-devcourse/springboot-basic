package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.exceptions.NoVoucherException;
import org.prgrms.kdt.utils.FileParser;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Profile("prod")
public class FileVoucherStorage implements VoucherStorage {
    private static final String NO_VOUCHER_EXCEPTION = "해당하는 ID를 가진 바우처가 없습니다.";

    private final FileParser fileParser;

    public FileVoucherStorage(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    @Override
    public void save(Voucher voucher) {
        fileParser.write(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return fileParser.getAllVouchers();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<String> voucherIds = fileParser.getVoucherIdList();

        return Optional.of(voucherIds.stream()
                .filter(readId -> voucherId.toString().equals(readId))
                .findFirst()
                .map(readId -> {
                    try {
                        return fileParser.getVoucherById(readId);
                    } catch (AmountException amountException){
                        throw new NoVoucherException(amountException.getMessage(), amountException);
                    }
                })
                .orElseThrow(() -> new NoVoucherException(NO_VOUCHER_EXCEPTION)));
    }
}
