package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean {

    @Value("${data.storage.name}")
    private String repositoryName;

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        return Optional.ofNullable(storage.put(voucher.getVoucherId(), voucher));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findByVoucherId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }

    @Override
    public List<Voucher> findByVoucherTerm(LocalDateTime beforeDate, LocalDateTime afterDate) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        System.out.println(MessageFormat.format("[Profile dev is set.] repositoryName is {0}", repositoryName));
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteVoucher(UUID voucherId, UUID customerId) {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[Profile dev is set.] repositoryName is {0}", repositoryName));
    }
}