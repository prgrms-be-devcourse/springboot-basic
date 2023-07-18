package com.example.demo.repository.voucher;

import com.example.demo.domain.voucher.Voucher;
import com.example.demo.domain.voucher.VoucherFactory;
import com.example.demo.enums.VoucherDiscountType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO vouchers VALUES (:id, :discountAmount, :discountType)";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("discountAmount", voucher.getDiscountAmount())
                .addValue("discountType", voucher.getVoucherType().name());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count != 1) {
            logger.error("바우처가 save되지 않았음. 잘 못된 voucher정보 : {}", voucher);
            throw new IllegalArgumentException(String.format("고객이 save되지 않았음. 잘 못된 voucher정보 : %s", voucher));
        }
        
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String sql = "SELECT * FROM vouchers WHERE id = :id";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id.toString());

        try {
            Voucher voucher = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("존재하지 않는 아이디가 입력되어 조회할 수 없음(Optional.empty()로 반환). 존재하지 않는 id = {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM vouchers";

        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, rowMapper());
        return vouchers;
    }

    @Override
    public void updateAmount(UUID id, int discountAmount) {
        String sql = "UPDATE vouchers SET amount = :amount WHERE id = :id";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("amount", discountAmount)
                .addValue("id", id.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.error("존재하지 않는 아이디가 입력되어 voucher 금액을 업데이트 할 수 없었음. 존재하지 않는 id = {}", id);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 voucher 금액을 업데이트 할 수 없었음. 존재하지 않는 id = %s", id));
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM vouchers WHERE id = :id";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", id.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.error("존재하지 않는 아이디가 입력되어 voucher를 삭제 할 수 없었음. 존재하지 않는 id = {}", id);
            throw new IllegalArgumentException(String.format("존재하지 않는 아이디가 입력되어 voucher를 삭제 할 수 없었음. 존재하지 않는 id = %s", id));
        }
    }

    @Override
    public boolean isVoucherNotExist(UUID id) {
        String sql = "SELECT * FROM vouchers WHERE id = :id";

        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id.toString());
        return namedParameterJdbcTemplate.query(sql, paramSource, (rs, count) -> 0).isEmpty();
    }

    private RowMapper<Voucher> rowMapper() {
        return (resultSet, count) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            int amount = resultSet.getInt("amount");
            VoucherDiscountType discountType = VoucherDiscountType.valueOf(resultSet.getString("discountType"));

            return VoucherFactory.createVoucher(id, amount, discountType);
        };
    }
}
