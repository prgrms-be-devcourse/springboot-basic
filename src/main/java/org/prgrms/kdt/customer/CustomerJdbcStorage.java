package org.prgrms.kdt.customer;

import org.prgrms.kdt.exceptions.CustomerException;
import org.prgrms.kdt.exceptions.InvalidDBAccessException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Profile("jdbc")
@Repository
public class CustomerJdbcStorage implements CustomerStorage {

    private static final int UPDATE_SUCCESS = 1;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        String customerId = resultSet.getString("customer_id");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);

        try {
            return new Customer(customerId, customerName, email, createdAt);
        } catch (CustomerException createException) {
            throw new InvalidDBAccessException(
                    MessageFormat.format("잘못된 값으로 고객을 생성할 수 없습니다. 값을 확인해주세요. -> id: {0}, name: {1}, email: {2}, create time: {3}",
                            customerId, customerName, email, createdAt));
        }
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerJdbcStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static Map<String, Object> createParamMap(Customer customer) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("customerId", customer.getCustomerId());
        paraMap.put("name", customer.getName());
        paraMap.put("email", customer.getEmail());
        paraMap.put("createdAt", customer.getCreatedAt());

        return paraMap;
    }

    @Override
    public void insert(Customer customer) {
        int update = namedParameterJdbcTemplate.update("INSERT INTO customer(customer_id, name, email, created_at) VALUES (:customerId, :name, :email, :createdAt)",
                createParamMap(customer));

        if (update != 1) {
            String errorDescription = MessageFormat.format("고객 저장에 실패했습니다. 전달한 값을 다시 한번 확인해주세요. customerId -> {0}, name -> {1}, email -> {2}, created_at -> {3}",
                    customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getCreatedAt());
            throw new InvalidDBAccessException(errorDescription);
        }
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject("select * from customer WHERE customer_id = :customerId",
                            Collections.singletonMap("customerId", customerId), customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query("select * from customer", customerRowMapper);
    }

    @Override
    public void deleteCustomerById(String customerId) {
        int update = namedParameterJdbcTemplate.update("DELETE FROM customer WHERE customer_id = :customerId",
                Collections.singletonMap("customerId", customerId));

        if (update != UPDATE_SUCCESS) {
            throw new InvalidDBAccessException(
                    MessageFormat.format(
                            "전달받은 ID에 대한 삭제가 성공하지 않았습니다. 사유: 해당 ID -> [{0}] 를 가진 바우처를 찾을 수 없음.", customerId));
        }
    }

    @Override
    public void update(Customer customer) {
        int update = namedParameterJdbcTemplate.update("UPDATE customer SET name = :name WHERE customer_id = :customerId",
                createParamMap(customer));

        if (update != 1) {
            throw new InvalidDBAccessException(
                    MessageFormat.format("고객 정보를 업데이트 하는 것에 실패하였습니다. 전달한 값을 다시 확인해주세요. name -> {0}, customerId -> {1}",
                            customer.getName(), customer.getCustomerId()));
        }
    }
}
