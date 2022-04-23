package org.prgms.customer.repository;

import org.prgms.customer.Customer;
import org.prgms.utils.UuidUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Repository
public class CustomerRepository {

    private final static String SELECT_ALL_QUERY = "SELECT * FROM customers;";
    private final static String SELECT_BY_NAME_QUERY = "SELECT * FROM customers WHERE name = ?;";
    private final static String SELECT_BY_ID = "SELECT * FROM customers WHERE customer_id = ?;";
    private final static String SELECT_BY_EMAIL = "SELECT * FROM customers WHERE email = ?;";

    private final static String INSERT_QUERY = "INSERT INTO customers(customer_id, name, email) values (?, ?, ?)";

    private final static String UPDATE_NAME_BY_ID_QUERY = "UPDATE customers SET name = ? WHERE customer_id = ?;";

    private final static String DELETE_QUERY = "DELETE FROM customers;";
    private final static String DELETE_QUERY_BY_ID = "DELETE FROM customers WHERE customer_id = ?;";


    private final JdbcTemplate jdbcTemplate;


    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, this::mapToCustomer);
    }


    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME_QUERY, this::mapToCustomer, name);
    }


    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapToCustomer, email));
    }


    public Optional<Customer> findById(UUID customerId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapToCustomer, UuidUtils.uuidToBytes(customerId)));
    }


    public int save(Customer customer) {
        var update = jdbcTemplate.update(INSERT_QUERY, UuidUtils.uuidToBytes(customer.customerId()), customer.name(), customer.email());

        checkArgument(update == 1, "데이터 저장 실패. 유효한 row 갯수가 1이 아님 : %s", update);

        return update;
    }


    public int deleteAll() {
        return jdbcTemplate.update(DELETE_QUERY);
    }


    public void deleteById(UUID customerId) {
        jdbcTemplate.update(DELETE_QUERY_BY_ID, UuidUtils.uuidToBytes(customerId));
    }


    public void update(Customer targetCustomer) {
        var update = jdbcTemplate.update(
                UPDATE_NAME_BY_ID_QUERY,
                targetCustomer.name(),
                UuidUtils.uuidToBytes(targetCustomer.customerId())
        );

        checkArgument(update == 1, "데이터 업데이트 실패. 유효한 row 갯수 1이 아님 : %s", update);
    }

    private Customer mapToCustomer(ResultSet resultSet, int rowNum) {
        try {
            var customerId = UuidUtils.bytesToUUID(resultSet.getBytes("customer_id"));
            var name = resultSet.getString("name");
            var email = resultSet.getString("email");
            return new Customer(customerId, name, email);
        } catch (SQLException e) {
            throw new DataRetrievalFailureException(MessageFormat.format("데이터를 가져오는 데 실패했습니다. {0}", e.getMessage()));
        }
    }
}
