package com.prgrms.vouchermanagement.core.voucher.repository.file;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Profile("dev")
@Repository
public class FileRepository implements VoucherRepository {

    private final FileStorage fileStorage;

    @Autowired
    public FileRepository(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    @Override
    public Voucher save(Voucher voucher) {
        fileStorage.saveFile(new VoucherVO(voucher.getId(), voucher.getName(), voucher.getAmount(), voucher.getVoucherType()));
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<VoucherVO> voucherVOList = fileStorage.readFile();
        return voucherVOList.stream()
                .map(it -> new Voucher(it.getVoucherID(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        fileStorage.deleteAll();
    }

    @Override
    public Optional<Voucher> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAllByIds(List<String> idList) {
        return null;
    }
}
