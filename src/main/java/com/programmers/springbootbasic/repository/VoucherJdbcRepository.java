package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository {

    private static final String INSERT
            = "INSERT INTO vouchers(voucher_id, fixed_amount, discount_percent, type) VALUES(?, ?, ?, ?)";
    private static final String SELECT_BY_ID
            = "SELECT * FROM vouchers WHERE voucher_id = ?";
    private static final String SELECT_NOT_IN_STATUS
            = "SELECT * FROM vouchers v WHERE v.voucher_id NOT IN " +
            "(SELECT s.voucher_id FROM status s)";
    private static final String SELECT_ALL
            = "SELECT * FROM vouchers";
    private static final String DELETE_BY_ID
            = "DELETE FROM vouchers WHERE voucher_id = ?";
    private static final String DELETE_ALL
            = "DELETE FROM vouchers";

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private static final RowMapper<VoucherDTO> vouchersRowMapper = (rs, rowNum) -> {
        String voucherId = rs.getString("voucher_id");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        Long fixed_amount = rs.getLong("fixed_amount");
        fixed_amount = fixed_amount == 0 ? null : fixed_amount;

        Integer discount_percent = rs.getInt("discount_percent");
        discount_percent = discount_percent == 0 ? null : discount_percent;

        Integer type = rs.getInt("type");

        return new VoucherDTO(UUID.fromString(voucherId), createdAt, fixed_amount, discount_percent, type);
    };

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VoucherJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public VoucherDTO insert(VoucherDTO voucherDTO) {
        int insertResult = jdbcTemplate.update(INSERT,
                voucherDTO.getVoucherId().toString(),
                voucherDTO.getFixedAmount(),
                voucherDTO.getDiscountPercent(),
                voucherDTO.getType());

        if (insertResult != 1)
            logger.error("새로운 할인권 저장 요청 실패");

        return voucherDTO;
    }

    @Override
    public Optional<VoucherDTO> findById(UUID voucherId) {
        try {
            VoucherDTO voucherDTO = jdbcTemplate.queryForObject(SELECT_BY_ID,
                    vouchersRowMapper,
                    voucherId.toString());
            return Optional.ofNullable(voucherDTO);
        } catch (DataAccessException e) {
            logger.info("존재하지 않은 할인권 아이디로 할인권 정보 검색 요청");
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherDTO> findAvailableVouchers() {
        return jdbcTemplate.query(SELECT_NOT_IN_STATUS, vouchersRowMapper);
    }

    @Override
    public List<VoucherDTO> findAll() {
        return jdbcTemplate.query(SELECT_ALL, vouchersRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        int deleteResult = jdbcTemplate.update(DELETE_BY_ID, voucherId.toString());

        if (deleteResult != 1)
            logger.info("존재하지 않은 할인권 아이디로 할인권 정보 삭제 요청");
    }

    @Override
    public void deleteAll() {
        int deleteResult = jdbcTemplate.update(DELETE_ALL);

        if (deleteResult == 0)
            logger.info("비어 있는 할인권 정보에 대하여 전체 할인권 삭제를 요청");
    }

}
