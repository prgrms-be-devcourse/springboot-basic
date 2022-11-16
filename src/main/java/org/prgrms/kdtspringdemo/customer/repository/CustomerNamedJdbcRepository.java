package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class CustomerNamedJdbcRepository implements CustomerRepository {
    private final String INSERT_SQL ="INSERT INTO customers_demo(customer_id, name, birth_date, email, created_at) VALUES(UUID_TO_BIN(:customerId),:name,:birth,:email,:createdAt)";
    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<Customer> customerRawMapper = (resultSet,i)->{
        String name = resultSet.getString("name");
        return null;
    };
    @Autowired
    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        jdbcTemplate.update(INSERT_SQL,toParamMap(customer));
        return customer;
    }

    @Override
    public void update(Customer customer) {
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findById(UUID targetId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID targetId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Customer> findAllBlackList() {
        return null;
    }

    private Map<String,Object> toParamMap(Customer customer){
        return new HashMap<>(){{
            put("customerId",customer.getCustomerId().toString().getBytes());
            put("name",customer.getName());
            put("birth",customer.getBirth());
            put("email",customer.getEmail());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastLoginAt",customer.getLastLoginAt()!=null? Timestamp.valueOf(customer.getLastLoginAt()):null);
        }};
    }
}
