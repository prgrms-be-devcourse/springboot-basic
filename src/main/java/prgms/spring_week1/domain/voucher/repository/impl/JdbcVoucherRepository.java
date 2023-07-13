package prgms.spring_week1.domain.voucher.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultset, i) -> {
        var voucherType = VoucherType.valueOf(resultset.getString("voucher_type"));
        var discount = resultset.getInt("discount");
        return new Voucher(voucherType, discount);
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", String.valueOf(voucher.getVoucherType()));
            put("discount", voucher.getDiscount());
            put("createdAt", voucher.getCreatedAt());
        }};
    }

    @Override
    public void insert(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO voucher(voucher_id,voucher_type, discount, created_at) VALUES (UUID_TO_BIN(:voucherId),:voucherType, :discount ,:createdAt)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return jdbcTemplate.query("select * from voucher", voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        try {
            return jdbcTemplate.query("select * from voucher WHERE voucher_type = :voucherType",
                    Collections.singletonMap("voucherType", voucherType),
                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(VoucherType voucherType) {
        jdbcTemplate.update("DELETE FROM voucher",new HashMap<>());
    }
}
