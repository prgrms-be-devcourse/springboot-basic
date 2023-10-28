package org.prgms.springbootbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.file.VoucherCsvFileManager;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev", "prod"})
@Primary
@Slf4j
public class VoucherCsvFileRepository implements VoucherRepository {
    private final ConcurrentHashMap<UUID, VoucherPolicy> vouchers = new ConcurrentHashMap<>();
    private final VoucherCsvFileManager voucherCsvFileManager;

    public VoucherCsvFileRepository(VoucherCsvFileManager voucherCsvFileManager) {
        log.debug("FileVoucherRepository started.");

        this.voucherCsvFileManager = voucherCsvFileManager;
    }

    @Override
    public Optional<VoucherPolicy> findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId));
    }

    @Override
    public List<VoucherPolicy> findAll() {
        return new ArrayList<>(vouchers.values());
    }

    @Override
    public VoucherPolicy create(VoucherPolicy voucherPolicy) {
        vouchers.putIfAbsent(voucherPolicy.getVoucherId(), voucherPolicy);
        return voucherPolicy;
    }

    @Override
    public Optional<VoucherPolicy> deleteById(UUID voucherId) {
        return Optional.ofNullable(vouchers.remove(voucherId));
    }

    @Override
    public void deleteAll() {
        vouchers.clear();
    }

    @PostConstruct
    private void fileRead(){
        List<VoucherPolicy> voucherPolicies = voucherCsvFileManager.read();
        voucherPolicies.forEach(this::create);
    }

    @PreDestroy
    private void fileWrite(){
        voucherCsvFileManager.write(this.findAll());
    }
}
