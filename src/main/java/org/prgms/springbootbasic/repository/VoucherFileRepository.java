package org.prgms.springbootbasic.repository;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.voucher.VoucherCsvFileManager;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.UUID;

@Repository
@Profile({"dev", "prod"})
@Primary
@Slf4j
public class VoucherFileRepository implements VoucherRepository {
    private final VoucherCsvFileManager voucherCsvFileManager;
    private final VoucherRepository voucherRepository;

    public VoucherFileRepository(VoucherCsvFileManager voucherCsvFileManager, VoucherMemoryRepository voucherRepository) {
        log.debug("FileVoucherRepository started.");

        this.voucherCsvFileManager = voucherCsvFileManager;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return voucherRepository.findById(voucherId);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher create(VoucherType type, int val) {
        return voucherRepository.create(type, val);
    }

    @Override
    public Voucher create(Voucher voucher) {
        return voucherRepository.create(voucher);
    }

    @PostConstruct
    private void fileRead(){
        List<Voucher> vouchers = voucherCsvFileManager.read();
        vouchers.forEach(voucherRepository::create);
    }

    @PreDestroy
    private void fileWrite(){
        voucherCsvFileManager.write(voucherRepository.findAll());
    }
}
