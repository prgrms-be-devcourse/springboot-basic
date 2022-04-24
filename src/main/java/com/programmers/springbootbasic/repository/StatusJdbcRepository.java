package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.CustomerDTO;
import com.programmers.springbootbasic.dto.StatusDTO;
import com.programmers.springbootbasic.dto.VoucherDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StatusJdbcRepository {

    private static final Logger logger = LoggerFactory.getLogger(StatusJdbcRepository.class);

    private static final String INSERT =
            "INSERT INTO status(customer_id, voucher_id) VALUES(?, ?)";
    private static final String SELECT_BY_CUSTOMER_ID =
            "SELECT * FROM status WHERE customer_id = ?";
    private static final String SELECT_BY_VOUCHER_ID =
            "SELECT * FROM status WHERE voucher_id = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM status";

    private static final String SELECT_VOUCHERS_BY_CUSTOMER_ID =
            "SELECT v.voucher_id, v.created_at, v.fixed_amount, v.discount_percent, v.type " +
            "From customers c, status s, vouchers v " +
            "WHERE s.voucher_id = v.voucher_id AND s.customer_id = c.customer_id AND s.customer_id = ?";
    private static final String SELECT_CUSTOMER_BY_VOUCHER_ID =
            "SELECT c.customer_id, c.name, s.registration_date " +
                    "FROM customers c, status s, vouchers v " +
                    "WHERE s.voucher_id = v.voucher_id AND s.customer_id = c.customer_id AND s.voucher_id = ?";

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<StatusDTO> statusRowMapper = (rs, rowNum) -> {
        String customerId = rs.getString("customer_id");
        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();

        return new StatusDTO(customerId, voucherId, registrationDate);
    };

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

    private static final RowMapper<CustomerDTO> customersRowMapper = (rs, rowNum) -> {
        String customerId = rs.getString("customer_id");
        String name = rs.getString("name");
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();

        return new CustomerDTO(customerId, name, registrationDate);
    };

    @Autowired
    public StatusJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public StatusDTO insert(String customerId, UUID voucherId) {
        StatusDTO statusDTO = new StatusDTO(customerId, voucherId);
        int insertResult = jdbcTemplate.update(INSERT, customerId, voucherId.toString());

        if (insertResult != 1)
            logger.error("새로운 상태 저장 요청 실패");

        return statusDTO;
    }

    public List<StatusDTO> findByCustomerId(String customerId) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER_ID, statusRowMapper, customerId);
    }

    public Optional<StatusDTO> findByVoucherId(UUID voucherId) {
        try {
            StatusDTO statusDTO = jdbcTemplate.queryForObject(SELECT_BY_VOUCHER_ID, statusRowMapper, voucherId.toString());
            return Optional.ofNullable(statusDTO);
        } catch (DataAccessException e) {
            logger.info("존재하지 않은 할인권 아이디로 상태 정보 검색 요청");
            return Optional.empty();
        }
    }

    public List<StatusDTO> findAll() {
        return jdbcTemplate.query(SELECT_ALL, statusRowMapper);
    }

    public List<VoucherDTO> findVouchersByCustomerId(String customerId) {
        return jdbcTemplate.query(SELECT_VOUCHERS_BY_CUSTOMER_ID, vouchersRowMapper, customerId);
    }

    public Optional<CustomerDTO> findCustomerByVoucherId(UUID voucherId) {
        try {
            CustomerDTO customerDTO = jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_VOUCHER_ID, customersRowMapper, voucherId.toString());
            return Optional.ofNullable(customerDTO);
        } catch (DataAccessException e) {
            logger.info("존재하지 않거나 또는 할당되지 않은 할인권 아이디로 해당 하는 할인권을 소유한 고객 정보 요청");
            return Optional.empty();
        }
    }

}