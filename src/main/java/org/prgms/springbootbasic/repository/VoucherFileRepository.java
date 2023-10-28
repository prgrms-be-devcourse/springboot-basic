package org.prgms.springbootbasic.repository;

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
public class VoucherFileRepository implements VoucherRepository { // csv를 다루니 이름은 Csv를 붙여 더 명확히 해야 하지 않나 싶다.
    private final ConcurrentHashMap<UUID, VoucherPolicy> vouchers = new ConcurrentHashMap<>();
    private final VoucherCsvFileManager voucherCsvFileManager;

    public VoucherFileRepository(VoucherCsvFileManager voucherCsvFileManager) {
        log.debug("FileVoucherRepository started.");

        this.voucherCsvFileManager = voucherCsvFileManager;
    }

    @Override
    public VoucherPolicy findById(UUID voucherId) {
        return Optional.ofNullable(vouchers.get(voucherId))
                .orElseThrow(NoSuchElementException::new);
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
