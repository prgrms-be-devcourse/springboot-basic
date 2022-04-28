package org.prgrms.springbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.domain.wallet.Wallet;
import org.prgrms.springbasic.utils.io.converter.FileManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbasic.utils.enumm.path.FilePath.VOUCHER_FILE_PATH;

@Slf4j
@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private final FileManager<Voucher> fileManager = new FileManager<>(VOUCHER_FILE_PATH.getPath());

    @Override
    public Voucher save(Voucher voucher) {
        fileManager.toFile(voucher);

        return voucher;
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        return fileManager.objectToList(Voucher.class)
                .stream()
                .filter(v -> v.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        //JDBC만 구현
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        //JDBC만 구현
        return null;
    }

    @Override
    public List<Voucher> findByCreatedPeriod(String from, String to) {
        return null;
    }

    @Override
    public List<Voucher> findVouchers() {
        return fileManager.objectToList(Voucher.class);
    }

    @Override
    public List<Wallet> findWallets() {
        //JDBC만 구현
        return null;
    }

    @Override
    public int countVouchers() {
        return fileManager.countLines();
    }

    @Override
    public Voucher update(Voucher voucher) {
        //JDBC만 구현
        return null;
    }

    @Override
    public boolean deleteByVoucherId(UUID voucherId) {
        //JDBC만 구현
        return true;
    }

    @Override
    public boolean deleteByCustomerId(UUID customerId) {
        //JDBC만 구현
        return true;
    }

    @Override
    public void deleteVouchers() {
        fileManager.clear();
    }
}