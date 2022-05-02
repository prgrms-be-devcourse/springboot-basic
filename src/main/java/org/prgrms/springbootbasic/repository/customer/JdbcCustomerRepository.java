package org.prgrms.springbootbasic.repository.customer;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.GOT_EMPTY_RESULT_MSG;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.NOTHING_WAS_INSERTED_EXP_MSG;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.customer.Email;
import org.prgrms.springbootbasic.entity.customer.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    //SQL
    private static final String SELECT_BY_ID = "SELECT * FROM customers WHERE customer_id = UUID_TO_BIN(?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM customers";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM customers WHERE email = ?";
    private static final String SELECT_BY_VOUCHER_TYPE_SQL =
        "SELECT DISTINCT c.customer_id as customer_id, c.name as name, c.email as email"
            + " FROM vouchers v JOIN customers c on v.customer_id = c.customer_id"
            + " WHERE v.type = ?";
    private static final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private static final String DELETE_ALL_SQL = "DELETE FROM customers";
    private static final String UPDATE_BY_ID_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";

    //Column
    private static final String COLUMN_CUSTOMER_ID = "customer_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Customer> mapToCustomer = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes(COLUMN_CUSTOMER_ID));
        var name = resultSet.getString(COLUMN_NAME);
        var email = resultSet.getString(COLUMN_EMAIL);
        return new Customer(customerId, new Name(name), new Email(email));
    };

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public List<Customer> findAll() {
        logger.info("findAll() called");

        return jdbcTemplate.query(SELECT_ALL_SQL, mapToCustomer);
    }

    @Override
    public UUID save(Customer customer) {
        logger.info("save() called");

        var insert = jdbcTemplate.update(INSERT_SQL,
            customer.getCustomerId().toString().getBytes(UTF_8),
            customer.getName().getName(),
            customer.getEmail().getEmail());
        if (insert != 1) {
            throw new RuntimeException(NOTHING_WAS_INSERTED_EXP_MSG.getMessage());
        }
        return customer.getCustomerId();
    }

    @Override
    public void removeAll() {
        logger.info("removeAll() called");

        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    @Override
    public UUID updateName(Customer customer) {
        logger.info("changeName() called");

        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
            customer.getName().getName(),
            customer.getCustomerId().toString().getBytes(UTF_8));
        if (update != 1) {
            throw new RuntimeException(GOT_EMPTY_RESULT_MSG.getMessage());
        }
        return customer.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        logger.info("findById() called");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID,
                mapToCustomer,
                customerId.toString().getBytes(UTF_8)));
        } catch (EmptyResultDataAccessException e) {
            logger.info("findById() Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        logger.info("findByEmail() called");

        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(SELECT_BY_EMAIL,
                    mapToCustomer,
                    email));
        } catch (EmptyResultDataAccessException e) {
            logger.info("findByEmail() Got empty result");
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByVoucherType(VoucherType type) {
        logger.info("findByVoucherType() called");

        return jdbcTemplate.query(SELECT_BY_VOUCHER_TYPE_SQL,
            mapToCustomer,
            type.name());
    }
}
