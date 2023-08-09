package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Operator;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Delete;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Insert;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Select;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Update;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Where;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Map.of;
import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;
import static org.prgrms.kdtspringdemo.customer.constant.CustomerColumn.ALL;
import static org.prgrms.kdtspringdemo.customer.constant.CustomerColumn.CUSTOMER_ID;
import static org.prgrms.kdtspringdemo.customer.constant.CustomerColumn.NICKNAME;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, i)
            -> new Customer(toUUID(resultSet.getBytes(CUSTOMER_ID.getColumn())), resultSet.getString(NICKNAME.getColumn()));

    private Map<String, Object> toParamMap(Customer customer) {
        return of(
                CUSTOMER_ID.getColumn(), customer.getId().toString().getBytes(),
                NICKNAME.getColumn(), customer.getNickname()
        );
    }

    @Override
    public Customer save(Customer customer) {
        Insert insert = Insert.into(Customer.class)
                .values(
                        of(
                                CUSTOMER_ID.getColumn(), "UUID_TO_BIN(:customer_id)",
                                NICKNAME.getColumn(), ":nickname"
                        )
                );

        jdbcTemplate.update(insert.getQuery(), toParamMap(customer));

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        Select selectId = Select.builder()
                .select(ALL.getColumn())
                .from(Customer.class)
                .where(
                        Where
                                .builder(
                                        CUSTOMER_ID.getColumn(), Operator.EQUALS, "UUID_TO_BIN(:customer_id)"
                                )
                                .build()
                )
                .build();

        return jdbcTemplate.query(selectId.getQuery(),
                        Collections.singletonMap(CUSTOMER_ID.getColumn(), id.toString().getBytes()),
                        customerRowMapper).stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> findByNickname(String nickname) {
        Select selectNickName = Select.builder()
                .select(ALL.getColumn())
                .from(Customer.class)
                .where(
                        Where
                                .builder(
                                        NICKNAME.getColumn(), Operator.EQUALS, ":nickname"
                                )
                                .build()
                )
                .build();

        return jdbcTemplate.query(selectNickName.getQuery(),
                        Collections.singletonMap(NICKNAME.getColumn(), nickname),
                        customerRowMapper).stream()
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        Select selectAll = Select.builder()
                .select(ALL.getColumn())
                .from(Customer.class)
                .build();

        return jdbcTemplate.query(selectAll.getQuery(), customerRowMapper);
    }

    @Override
    public void update(Customer customer) {
        Update update = Update.builder()
                .update(Customer.class)
                .set(of(
                        NICKNAME.getColumn(), ":nickname"
                ))
                .where(
                        Where
                                .builder(CUSTOMER_ID.getColumn(), Operator.EQUALS, "UUID_TO_BIN(:customer_id)")
                                .build()
                )
                .build();

        jdbcTemplate.update(update.getQuery(), toParamMap(customer));
    }

    @Override
    public void deleteById(UUID id) {
        Delete delete = Delete.builder()
                .delete(Customer.class)
                .where(
                        Where
                                .builder(CUSTOMER_ID.getColumn(), Operator.EQUALS, "UUID_TO_BIN(:customer_id)")
                                .build()
                )
                .build();

        jdbcTemplate.update(delete.getQuery(),
                Collections.singletonMap(CUSTOMER_ID.getColumn(), id.toString().getBytes()));
    }
}
