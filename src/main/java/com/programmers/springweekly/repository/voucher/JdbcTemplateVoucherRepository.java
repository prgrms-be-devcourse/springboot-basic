package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@Slf4j
@Profile({"local", "test"})
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher values(:voucherId, :discountAmount, :voucherType)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("discountAmount", voucher.getVoucherAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());

        try {
            template.update(sql, param);
            return voucher;
        } catch (DuplicateKeyException e) {
            log.warn("바우처를 데이터베이스에 저장하던 중 이미 테이블안에 동일한 ID를 가진 키가 있어서 예외 발생 {}", voucher.getVoucherId(), e);
            throw new DuplicateKeyException("이미 있는 바우처 ID입니다. 관리자에게 문의해주세요.");
        }
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "update voucher set voucher_discount_amount = :discountAmount, voucher_type = :voucherType where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("discountAmount", voucher.getVoucherAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());

        template.update(sql, param);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from voucher where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);
        try {
            Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            log.warn("바우처 ID로 바우처를 찾을 수 없어서 예외 발생, Optional Empty로 반환 {}", voucherId, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";

        return template.query(sql, voucherRowMapper());
    }

    @Override
    public int deleteById(UUID voucherId) {
        String sql = "delete from voucher where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);

        return template.update(sql, param);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from voucher";

        SqlParameterSource param = new MapSqlParameterSource();

        template.update(sql, param);
        log.warn("주의, voucher 테이블에 있는 데이터 모두 삭제처리 됨.");
    }

    @Override
    public boolean existById(UUID voucherId) {
        String sql = "select * from voucher where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);
        try {
            template.queryForObject(sql, param, voucherRowMapper());
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.warn("바우처 ID가 존재하는지 체크했으나 없어서 예외 발생 {}", voucherId, e);
            return false;
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return ((resultSet, rowMap) ->
                VoucherFactory.createVoucher(UUID.fromString(resultSet.getString("voucher_id")),
                        VoucherType.valueOf(resultSet.getString("voucher_type")),
                        Long.parseLong(resultSet.getString("voucher_discount_amount"))
                )
        );
    }

}
