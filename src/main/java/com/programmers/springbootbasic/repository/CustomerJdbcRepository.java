package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.dto.CustomerDTO;
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

@Repository
public class CustomerJdbcRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private static final String INSERT =
            "INSERT INTO customers(customer_id, name) VALUES(?, ?)";
    private static final String SELECT_ALL =
            "SELECT * FROM customers";
    private static final String SELECT_BY_ID =
            "SELECT * FROM customers WHERE customer_id = ?";
    private static final String DELETE_BY_ID =
            "DELETE FROM customers WHERE customer_id = ?";
    private static final String DELETE_ALL =
            "DELETE FROM customers";

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<CustomerDTO> customersRowMapper = (rs, rowNum) -> {
        String customerId = rs.getString("customer_id");
        String name = rs.getString("name");
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();

        return new CustomerDTO(customerId, name, registrationDate);
    };

    @Autowired
    public CustomerJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CustomerDTO insert(CustomerDTO customerDTO) {
        int insertResult = jdbcTemplate.update(INSERT, customerDTO.getCustomerId(), customerDTO.getName());

        if (insertResult != 1)
            logger.error("새로운 고객 정보 저장 실패");

        return customerDTO;
    }

    public Optional<CustomerDTO> findById(String customerId) {
        try {
            CustomerDTO customerDTO = jdbcTemplate.queryForObject(SELECT_BY_ID, customersRowMapper, customerId);
            return Optional.ofNullable(customerDTO);
        } catch (DataAccessException e) {
            logger.info("존재하지 않은 고객 아이디로 고객 정보 검색 요청");
            return Optional.empty();
        }
    }

    public List<CustomerDTO> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customersRowMapper);
    }

    public void deleteById(String customerId) {
        int deleteResult = jdbcTemplate.update(DELETE_BY_ID, customerId);

        if (deleteResult != 1)
            logger.info("존재하지 않은 고객 아이디로 고객 정보 삭제 요청");
    }

    public void deleteAll() {
        int deleteResult = jdbcTemplate.update(DELETE_ALL);

        if (deleteResult == 0)
            logger.info("비어 있는 고객 정보에 대하여 전체 고객 삭제를 요청");
    }

}
