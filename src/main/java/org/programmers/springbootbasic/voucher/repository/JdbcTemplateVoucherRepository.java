package org.programmers.springbootbasic.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.*;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.springbootbasic.console.converter.UuidByteArrayConverter.bytesToUuid;
import static org.programmers.springbootbasic.console.converter.UuidByteArrayConverter.uuidToBytes;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

@Slf4j
@Repository
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    public static final String PARAM_KEY_VOUCHER_ID = "voucherId";
    public static final String PARAM_KEY_AMOUNT = "amount";
    public static final String PARAM_KEY_TYPE = "type";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL =
            "INSERT into voucher(voucher_id, amount, type) values (:"
                    + PARAM_KEY_VOUCHER_ID + ", :" + PARAM_KEY_AMOUNT + ", :" + PARAM_KEY_TYPE + ")";
    private static final String FIND_BY_ID_SQL =
            "SELECT * from voucher WHERE voucher_id = :" + PARAM_KEY_VOUCHER_ID;
    private static final String FIND_ALL_SQL = "SELECT * from voucher";
    private static final String REMOVE_SQL = "DELETE from voucher WHERE voucher_id = :" + PARAM_KEY_VOUCHER_ID;

    public JdbcTemplateVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    //TODO PR 포인트: 해당 예외 사용이 적절한지?
    @Override
    public Voucher insert(Voucher voucher) {
        int updatedRow = jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
        if (1 != updatedRow) {
            log.error("바우처가 정상적으로 저장되지 않았습니다. updatedRow={}", updatedRow);
            throw new IncorrectResultSizeDataAccessException(updatedRow);
        }
        return voucher;
    }


    //TODO: Customer 완성 후, Voucher 할당하는 update 메서드 작성하기

    private SqlParameterSource toParamMap(Voucher voucher) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue(PARAM_KEY_VOUCHER_ID, uuidToBytes(voucher.getId()));
        paramSource.addValue(PARAM_KEY_AMOUNT, voucher.getAmount());
        paramSource.addValue(PARAM_KEY_TYPE, voucher.getType().toString());

        return paramSource;
    }

    //TODO: PR 포인트: 왜 queryForObject 사용하지 않았는지: 예외는 정상 요청 흐름에서는 발생해서는 안 된다!
    //TODO: PR 포인트: 결과 값이 음수일 경우 다른 예외가 나올 것이기에
    //TODO: PK 기반 조회니까 터지는 게 맞지 않을까 싶다.
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        var paramSource = new MapSqlParameterSource(PARAM_KEY_VOUCHER_ID, uuidToBytes(voucherId));
        List<Voucher> result = jdbcTemplate.query(FIND_BY_ID_SQL, paramSource, voucherRowMapper());
        switch (result.size()) {
            case 0:
                return Optional.empty();
            case 1:
                return Optional.of(result.get(0));
            default:
                log.error("동일 id에 2개 이상의 결과가 조회되었습니다. 해당 voucherId={}, 조회 결과 수={}", voucherId, result.size());
                throw new IncorrectResultSizeDataAccessException(result.size());
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = bytesToUuid(rs.getBytes("voucher_id"));
            int amount = rs.getInt("amount");
            String type = rs.getString("type");

            if (FIXED.toString().equals(type)) {
                return new FixedDiscountVoucher(voucherId, amount);
            } else if (RATE.toString().equals(type)) {
                return new RateDiscountVoucher(voucherId, amount);
            }
            log.error("잘못된 바우처 타입입니다. type={}", type);
            throw new IllegalVoucherTypeException("잘못된 바우처 타입입니다.");
        };
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper());
    }

    @Override
    public void remove(UUID voucherId) {
        var paramSource = new MapSqlParameterSource(PARAM_KEY_VOUCHER_ID, uuidToBytes(voucherId));
        int deletedRow = jdbcTemplate.update(REMOVE_SQL, paramSource);
        if (deletedRow != 1) {
            log.error("바우처가 정상적으로 삭제되지 않았습니다. deletedRow={}", deletedRow);
            throw new IllegalStateException("바우처가 정상적으로 삭제되지 않았습니다.");
        }
    }
}