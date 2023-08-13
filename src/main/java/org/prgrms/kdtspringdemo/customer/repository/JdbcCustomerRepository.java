package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Delete;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Insert;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Select;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Update;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Where;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Map.of;
import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;
import static org.prgrms.kdtspringdemo.customer.constant.CustomerColumn.CUSTOMER_ID;
import static org.prgrms.kdtspringdemo.customer.constant.CustomerColumn.NICKNAME;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Operator.EQ;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID uuid = toUUID(resultSet.getBytes(CUSTOMER_ID.getColumn()));
        String nickname = resultSet.getString(NICKNAME.getColumn());

        return new Customer(uuid, nickname);
    };

    @Override
    public Customer save(Customer customer) {
        Insert insert = Insert.into(Customer.class)
                .values(
                        of(
                                CUSTOMER_ID.getColumn(), customer.getId(),
                                NICKNAME.getColumn(), customer.getNickname()
                        )
                );

        jdbcTemplate.update(insert.getQuery());

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        Select selectId = Select.builder()
                .select()
                .from(Customer.class)
                .where(
                        Where
                                .builder(
                                        CUSTOMER_ID.getColumn(), EQ, id
                                )
                                .build()
                )
                .build();

        return jdbcTemplate.query(selectId.getQuery(), customerRowMapper).stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> findByNickname(String nickname) {
        Select selectNickName = Select.builder()
                .select()
                .from(Customer.class)
                .where(
                        Where
                                .builder(
                                        NICKNAME.getColumn(), EQ, nickname
                                )
                                .build()
                )
                .build();

        return jdbcTemplate.query(selectNickName.getQuery(), customerRowMapper).stream()
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        Select selectAll = Select.builder()
                .select()
                .from(Customer.class)
                .build();

        return jdbcTemplate.query(selectAll.getQuery(), customerRowMapper);
    }

    @Override
    public void update(Customer customer) {
        Update update = Update.builder()
                .update(Customer.class)
                .set(of(
                        CUSTOMER_ID.getColumn(), customer.getId(),
                        NICKNAME.getColumn(), customer.getNickname()
                ))
                .where(
                        Where
                                .builder(CUSTOMER_ID.getColumn(), EQ, customer.getId())
                                .build()
                )
                .build();

        jdbcTemplate.update(update.getQuery());
    }

    @Override
    public void deleteById(UUID id) {
        Delete delete = Delete.builder()
                .delete(Customer.class)
                .where(
                        Where
                                .builder(CUSTOMER_ID.getColumn(), EQ, id)
                                .build()
                )
                .build();

        jdbcTemplate.update(delete.getQuery());
    }
}
