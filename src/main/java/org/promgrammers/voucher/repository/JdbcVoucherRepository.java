package org.promgrammers.voucher.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.promgrammers.voucher.domain.Voucher;
import org.promgrammers.voucher.util.VoucherUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SAVE = "INSERT INTO vouchers(id, amount, voucher_type) VALUES(:id, " +
            ":amount, :voucherType)";
    private static final String FIND_BY_ID = "SELECT * FROM vouchers WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM vouchers";
    private static final String UPDATE = "UPDATE vouchers SET amount = :amount, voucher_type = :voucherType WHERE " +
            "id = :id";
    private static final String DELETE_ALL = "DELETE FROM vouchers";


    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            Map<String, UUID> param = Map.of("id", id);
            Voucher voucher = jdbcTemplate.queryForObject(FIND_BY_ID, param, VoucherUtils.voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            log.error("해당하는 바우처를 찾을 수 없습니다." + id);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, VoucherUtils.voucherRowMapper);
    }

    @Override
    public Voucher save(Voucher voucher) {
        SqlParameterSource parameterSource = VoucherUtils.createParameterSource(voucher);
        int update = jdbcTemplate.update(SAVE, parameterSource);

        if (update != 1) {
            throw new DataAccessException("이미 존재하는 바우처 입니다." + voucher.getId()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(UPDATE, VoucherUtils.createParameterSource(voucher));
        return voucher;
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());

    }
}
