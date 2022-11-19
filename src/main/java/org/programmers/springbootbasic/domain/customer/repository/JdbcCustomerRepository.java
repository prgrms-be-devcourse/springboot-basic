package org.programmers.springbootbasic.domain.customer.repository;

import org.programmers.springbootbasic.domain.customer.model.Customer;
import org.programmers.springbootbasic.domain.customer.dto.CustomerInsertDto;
import org.programmers.springbootbasic.exception.CanNotDeleteException;
import org.programmers.springbootbasic.exception.CanNotInsertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        long customerId = resultSet.getLong("customer_id");
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, name, email, createdAt);
    };

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }


    @Override
    public Optional<Customer> findById(long customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = ?",
                    customerRowMapper,
                    customerId));
        } catch (EmptyResultDataAccessException e) {
            logger.info("검색 결과가 없습니다.");
            return Optional.empty();
        }
    }


    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email = ?",
                    customerRowMapper,
                    email));
        } catch (EmptyResultDataAccessException e) {
            logger.info("검색 결과가 없습니다.");
            return Optional.empty();
        }
    }


    @Override
    public void save(CustomerInsertDto customerInsertDto) {
        int insertRow = 0;
        try {
            insertRow = jdbcTemplate.update("insert into customers(name, email) values (?, ?)",
                    customerInsertDto.name(),
                    customerInsertDto.email()
            );
            if(insertRow != 1) throw new CanNotInsertException("Customer를 데이터베이스에 쓸 수 없습니다.");
        } catch (DataAccessException e) {
            logger.error("Customer를 데이터베이스에 쓸 수 없습니다.");
            throw new CanNotInsertException(e.getMessage(), e);
        }
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers");
    }

    @Override
    public void deleteById(long customerId) {
        try {
            int deleteRow = jdbcTemplate.update("delete from customers where customer_id = ?", customerId);
            if(deleteRow != 1) throw new CanNotDeleteException("Customer를 삭제할 수 없습니다.");
        } catch(DataAccessException e) {
            throw new CanNotDeleteException("Customer를 삭제할 수 없습니다.");
        }
    }
}
