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

// 데코레이터 패턴: 기능의 확장. 이것이 기능의 확장이라 보기는 어렵다.
// 빈은 싱글톤이다. 만약 미래에 VoucherFileRepository와 VoucherMemoryRepository를 둘 다 쓴다면
// VoucherFileRepository에서 VoucherMemoryRepository 내 HashMap을 쓸 건데 싱글톤이라 결국 둘을 공존해 쓰게 되고 이로 인한 이슈가 있을 수 있다.
// 둘을 분리하려면 new로 따로 인스턴스 생성해서 넣어줘도 해결을 할 수 있지만 이를 인지하면서 개발하는 것부터 스트레스임.
// 그냥 분리하자.
