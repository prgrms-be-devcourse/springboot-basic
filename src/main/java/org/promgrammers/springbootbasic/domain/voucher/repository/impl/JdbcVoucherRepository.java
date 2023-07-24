package org.promgrammers.springbootbasic.domain.voucher.repository.impl;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate template;

    private static final String INSERT = "INSERT INTO vouchers(voucher_id, amount, voucher_type) VALUES(:voucherId, :amount, :voucherType)";
    private static final String FIND_BY_ID = "SELECT * FROM vouchers WHERE voucher_id = :voucherId";
    private static final String FIND_ALL = "SELECT * FROM vouchers";
    private static final String UPDATE = "UPDATE vouchers SET amount = :amount, voucher_type = :voucherType WHERE voucher_id = :voucherId";
    private static final String DELETE_ALL = "DELETE FROM vouchers";
    private static final String DELETE_BY_ID = "DELETE FROM vouchers WHERE voucher_id = :voucherId";
    private static final String INSERT_ASSIGN_CUSTOMER = "UPDATE vouchers SET customer_id = :customerId WHERE voucher_id = :voucherId";
    private static final String FIND_ALL_BY_CUSTOMER_ID = "SELECT * FROM vouchers WHERE customer_id = :customerId";
    private static final String FIND_ALL_BY_TYPE = "SELECT * FROM vouchers WHERE voucher_type = :voucherType";
    private static final String DELETE_BY_CUSTOMER_ID = "UPDATE vouchers SET customer_id = NULL WHERE customer_id = :customerId AND voucher_id = :voucherId";

    public JdbcVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    private SqlParameterSource createParameterSource(Voucher voucher) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString())
                .addValue("amount", voucher.getAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());
        return paramSource;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        SqlParameterSource parameterSource = createParameterSource(voucher);
        int insertCount = template.update(INSERT, parameterSource);

        if (insertCount != 1) {
            throw new DataAccessException("바우처 저장에 실패 했습니다. => " + voucher.getVoucherId()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return voucher;
    }

    @Override
    public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        Map<String, Object> params = Map.of("customerId", customerId, "voucherId", voucherId);
        template.update(INSERT_ASSIGN_CUSTOMER, params);
    }

    @Override
    public List<Voucher> findAllByCustomerId(UUID customerId) {
        Map<String, Object> params = Map.of("customerId", customerId);
        return template.query(FIND_ALL_BY_CUSTOMER_ID, params, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        Map<String, Object> params = Map.of("voucherType", voucherType.toString());
        return template.query(FIND_ALL_BY_TYPE, params, voucherRowMapper);
    }

    @Override
    public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
        Map<String, Object> params = Map.of("customerId", customerId, "voucherId", voucherId);
        template.update(DELETE_BY_CUSTOMER_ID, params);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Map<String, Object> param = Map.of("voucherId", voucherId);
            Voucher voucher = template.queryForObject(FIND_BY_ID, param, voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 ID를 찾지 못했습니다. => " + voucherId);
            return Optional.empty();
        }
    }


    @Override
    public List<Voucher> findAll() {
        return template.query(FIND_ALL, voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        template.update(UPDATE, createParameterSource(voucher));
        return voucher;
    }

    @Override
    public void deleteAll() {
        template.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, Object> param = Map.of("voucherId", voucherId);
        template.update(DELETE_BY_ID, param);
    }

    private final RowMapper<Voucher> voucherRowMapper = ((rs, rowNum) -> {
        String voucherId = rs.getString("voucher_id");
        long amount = rs.getLong("amount");
        VoucherType voucherType = VoucherType.fromTypeString(rs.getString("voucher_type"));
        String customerId = rs.getString("customer_id");
        UUID mappedCustomerId = (customerId == null) ? null : UUID.fromString(customerId);

        Voucher voucher = VoucherFactory.convertToVoucher(UUID.fromString(voucherId), voucherType, amount);
        voucher.assignCustomerId(mappedCustomerId);

        return voucher;
    });
}