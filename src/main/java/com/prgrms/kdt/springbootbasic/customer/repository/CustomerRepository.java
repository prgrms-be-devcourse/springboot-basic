package com.prgrms.kdt.springbootbasic.customer.repository;

import com.prgrms.kdt.springbootbasic.customer.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class CustomerRepository {
    private final Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Customer customer){
        return new HashMap<>(){{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
        }};
    }

    public static RowMapper<Customer> customerRowMapper = (resultSet, i) ->{
        var byteBuffer = ByteBuffer.wrap(resultSet.getBytes("customer_id"));
        UUID customerId = new UUID(byteBuffer.getLong(),byteBuffer.getLong());
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        return new Customer(customerId,name,email);
    };


    public Optional<Customer> saveCustomer(Customer customer){
        var insertResult = jdbcTemplate.update("INSERT INTO customers(customer_id,name,email) VALUES(UNHEX(REPLACE( :customerId, '-', '')), :name, :email)", toParamMap(customer));
        if (insertResult != 1){
            return Optional.empty();
        }
        return Optional.of(customer);
    }

    public Optional<Customer> findCustomerById(UUID customerId){
        try{
            Customer findCustomer = jdbcTemplate.queryForObject("SELECT * FROM customers where customer_id = UNHEX(REPLACE( :customerId, '-', ''))",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),customerRowMapper);
            return Optional.of(findCustomer);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Customer> findCustomerByEmail(String email){
        try{
            Customer findCustomer = jdbcTemplate.queryForObject("SELECT * FROM customers where email = :email",
                    Collections.singletonMap("email",email),customerRowMapper);
            return Optional.of(findCustomer);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public List<Customer> getAllCustomers(){
        return jdbcTemplate.query("Select * from customers",customerRowMapper);
    }


    public Optional<Customer> updateCustomer(Customer customer){
        var updateResult = jdbcTemplate.update("UPDATE customers SET name = :name where customer_id = UNHEX(REPLACE( :customerId, '-', ''))", toParamMap(customer));
        if (updateResult != 1){
            return Optional.empty();
        }
        return Optional.of(customer);
    }

    public boolean deleteCustomer(Customer customer){
        var deleteResult = jdbcTemplate.update("DELETE FROM customers where customer_id = UNHEX(REPLACE( :customerId, '-', ''))", toParamMap(customer));
        if(deleteResult!=1)
            return false;
        return true;
    }
}
