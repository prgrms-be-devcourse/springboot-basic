package org.prgms.voucheradmin.domain.customer.dao.customer;

import static org.prgms.voucheradmin.global.util.Util.toUUID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.global.exception.customexception.CreationFailException;
import org.prgms.voucheradmin.global.exception.customexception.UpdateFailException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * 고객을 DB에 CRUD를 하는 클래스 입니다.
 **/
@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 고객 생성 메서드
     **/
    @Override
    public Customer create(Customer customer) {
        int update = jdbcTemplate.update("insert into customers(customer_id, name, email, created_at) values(UUID_TO_BIN(?), ?, ?, ?)",
                customer.getCustomerId().toString().getBytes(), customer.getName(), customer.getEmail(), customer.getCreatedAt());

        if(update != 1) {
            throw new CreationFailException();
        }

        return customer;
    }

    /**
     * 고객 목록 조회 메서드
     **/
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }


    /**
     * 바우처르 소유하고 있는 고객 목록 조회 메서드
     **/
    @Override
    public List<Customer> findVoucherOwners(UUID voucherId) {
        return jdbcTemplate.query("select c.customer_id, c.name, c.email, created_at from voucher_wallets as vw join customers as c on vw.customer_id = c.customer_id where voucher_id = UUID_TO_BIN(?)", customerRowMapper,
                voucherId.toString().getBytes());
    }

    /**
     * id를 통한 고객 조회 메서드
     **/
    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from customers where customer_id = UUID_TO_BIN(?)", customerRowMapper, customerId.toString().getBytes()));
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * email을 통한 고객 조회 메서드
     **/
    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("select * from customers where email = ?", customerRowMapper, email));
        }catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * 고객(이름) 수정 메서드
     **/
    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("update customers set name = ? where customer_id = UUID_TO_BIN(?)",
                customer.getName(),
                customer.getCustomerId().toString().getBytes());

        if(update != 1) {
            throw new UpdateFailException();
        }

        return customer;
    }

    /**
     * 고객 삭제 메서드
     **/
    @Override
    public void delete(Customer customer) {
        jdbcTemplate.update("delete from customers where customer_id = UUID_TO_BIN(?)",
                customer.getCustomerId().toString().getBytes());
    }

    /**
     * 조회 결과를 entity에 매핑하는 메서드
     **/
    private final RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return Customer.builder()
                .customerId(customerId)
                .name(name)
                .email(email)
                .createdAt(createdAt)
                .build();
    };
}
