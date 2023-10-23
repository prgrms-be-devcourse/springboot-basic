package com.prgrms.vouchermanagement.core.voucher.repository.file;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Profile("prod")
@Repository
public class FileRepository implements VoucherRepository {

    private final FileStorage fileStorage;
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Autowired
    public FileRepository(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public Voucher save(Voucher voucher) {
        long nextId = idGenerator.incrementAndGet();
        voucher.setVoucherID(nextId);
        fileStorage.saveFile(new VoucherVO(voucher.getVoucherID(), voucher.getName(), voucher.getAmount(), voucher.getVoucherType()));
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<VoucherVO> voucherVOList = fileStorage.readFile();
        return voucherVOList.stream()
                .map(it -> new Voucher(it.getVoucherID(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
    }
}
