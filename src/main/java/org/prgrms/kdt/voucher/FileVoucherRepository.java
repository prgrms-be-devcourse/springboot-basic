package org.prgrms.kdt.voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.file.FileRepository;
import org.prgrms.kdt.util.FileUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 11:56 오후
 */
@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository, FileRepository<Voucher> {

    private static final String FILE_NAME = "voucher.txt";

    @Override
    public Voucher insert(Voucher voucher) {
        saveFile(voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        readFile();
        return Collections.emptyMap();
    }

    @Override
    public void saveFile(Voucher voucher) {
        FileUtil.write(voucher.toString(), new ClassPathResource(FILE_NAME));
    }

    @Override
    public void readFile() {
        FileUtil.readText(new ClassPathResource(FILE_NAME));
    }

}

