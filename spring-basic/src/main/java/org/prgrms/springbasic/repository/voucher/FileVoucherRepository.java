package org.prgrms.springbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.utils.io.converter.FileManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.springbasic.utils.enumm.path.FilePath.VOUCHER_FILE_PATH;

@Profile("dev")
@Repository
@Slf4j
public class FileVoucherRepository implements VoucherRepository {

    private final FileManager<Voucher> fileManager = new FileManager<>(VOUCHER_FILE_PATH.getPath());

    @Override
    public Voucher save(Voucher voucher) {
        fileManager.toFile(voucher);

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return fileManager.objectToList(Voucher.class)
                .stream()
                .filter(v -> v.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        return fileManager.objectToList(Voucher.class);
    }

    @Override
    public int countStorageSize() {
        return fileManager.countLines();
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        //JDBC만 구현
        return null;
    }

    @Override
    public void clear() {
        fileManager.clear();
    }
}