package org.prgrms.spring_week1.customer.repository;

import static org.prgrms.spring_week1.jdbcUtils.toLocalDateTime;
import static org.prgrms.spring_week1.jdbcUtils.toUUID;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.customer.model.Customer;
import org.prgrms.spring_week1.customer.model.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> getAll() {
        return jdbcTemplate.query("select * from Customer", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.of(jdbcTemplate
                .queryForObject(
                    "select * from Customer where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result : e");
            return Optional.empty();
        }

    }

    @Override
    public Customer insert(Customer customer) {
        try {
            int insert = jdbcTemplate.update(
                "insert into Customer(customer_id, gender, name, phone_number, address, created_at, updated_at)"
                    +
                    "values(UUID_TO_BIN(:customerId), :gender, :name, :phoneNumber, :address, :createdAt, :updatedAt)",
                toParamMap(customer));
            if (insert != 1) {
                throw new RuntimeException("Nothing was inserted");
            }

            return customer;
        } catch (DataAccessException e){
            logger.error("Get error during insert customer : ", e);
            throw e;
        }
    }

    @Override
    public Customer update(Customer customer) {
        try {
            int update = jdbcTemplate.update("update Customer "
                + "set name = :name, gender = :gender, address = :address, phone_number = :phoneNumber, created_at = :createdAt, updated_at = :updatedAt "
                + "where customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
            if (update != 1) {
                throw new RuntimeException("Nothing was updated");
            }
            return customer;
        } catch (DataAccessException e){
            logger.error("Get error during update customer : ", e);
            throw e;
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from Customer", Collections.emptyMap());
    }

    private static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("customer_id"));
        Gender gender = Gender.valueOf(resultSet.getString("gender"));
        String address = resultSet.getString("address");
        String name = resultSet.getString("name");
        String phoneNumber = resultSet.getString("phone_number");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Customer(voucherId, name, address, gender, phoneNumber, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("customerId", customer.getCustomerId().toString().getBytes());
        objectHashMap.put("name", customer.getName());
        objectHashMap.put("address", customer.getAddress());
        objectHashMap.put("gender", customer.getGender().toString());
        objectHashMap.put("phoneNumber", customer.getPhoneNumber());
        objectHashMap.put("createdAt", customer.getCreatedAt());
        objectHashMap.put("updatedAt", customer.getUpdatedAt());

        return objectHashMap;

    }

}
