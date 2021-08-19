package org.prgrms.kdt.voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/19 Time: 4:47 오후
 */

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.file.FileRepository;
import org.prgrms.kdt.file.FileUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 11:56 오후
 */
@Primary
@Repository
public class FileVoucherRepository implements VoucherRepository, FileRepository<Voucher> {

    private static final String FILE_NAME = "voucher.text";

    @Override
    public void save(Voucher voucher) {
        saveFile(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return Collections.emptyMap();
    }

    @Override
    public void saveFile(Voucher voucher) {
        FileUtil.write(voucher.toString(), FILE_NAME);
    }

}

