package org.prgrms.application.repository.voucher;

import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.repository.customer.CustomerJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private static final int HAS_UPDATE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    public MemoryVoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> copiedVouchers = new ArrayList<>();
        for(Voucher voucher : storage.values()){
            copiedVouchers.add(voucher.copy());
        }
        return copiedVouchers;
    }

    @Override
    public Optional<Voucher> findById() {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }
}
