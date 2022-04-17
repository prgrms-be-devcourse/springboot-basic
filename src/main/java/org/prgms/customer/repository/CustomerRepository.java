package org.prgms.customer.repository;

import org.prgms.customer.Customer;
import org.prgms.utils.UuidUtils;
import org.prgms.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

@Repository
public class CustomerRepository {
    private final static Logger logger = LoggerFactory.getLogger(CustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final VoucherRepository voucherRepository;

    private final static String SELECT_ALL_QUERY = "SELECT a.*, b.voucher_id FROM customers a LEFT JOIN vouchers b ON a.customer_id = b.customer_id;";
    private final static String SELECT_BY_NAME_QUERY = "SELECT a.*, b.voucher_id FROM customers a LEFT JOIN vouchers b ON a.customer_id = b.customer_id WHERE a.name = ?;";
    private final static String SELECT_BY_ID = "SELECT a.*, b.voucher_id FROM customers a LEFT JOIN vouchers b ON a.customer_id = b.customer_id WHERE a.customer_id = ?;";
    private final static String SELECT_BY_EMAIL = "SELECT a.*, b.voucher_id FROM customers a LEFT JOIN vouchers b ON a.customer_id = b.customer_id WHERE a.email = ?;";

    private final static String INSERT_QUERY = "INSERT INTO customers(customer_id, name, email) values (?, ?, ?)";
    private final static String UPDATE_NAME_BY_ID_QUERY = "UPDATE customers SET name = ? WHERE customer_id = ?;";
    private final static String DELETE_QUERY = "DELETE FROM customers;";
    private final static String DELETE_QUERY_BY_ID = "DELETE FROM customers WHERE customer_id = ?;";

    public CustomerRepository(JdbcTemplate jdbcTemplate, VoucherRepository voucherRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.voucherRepository = voucherRepository;
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, this::mapToCustomer);
    }

    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(SELECT_BY_NAME_QUERY, this::mapToCustomer, name);
    }

    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query(SELECT_BY_EMAIL, this::mapToCustomer, email);
    }

    public List<Customer> findById(UUID customerId) {
        return jdbcTemplate.query(SELECT_BY_ID, this::mapToCustomer, UuidUtils.uuidToBytes(customerId));
    }

    public int insert(Customer customer) {
        var update = jdbcTemplate.update(INSERT_QUERY, UuidUtils.uuidToBytes(customer.customerId()), customer.name(), customer.email());
        customer.getVouchers().forEach(voucherRepository::save);
        if (update != 1) {
            throw new DataIntegrityViolationException(MessageFormat.format("데이터 삽입 실패, 유효 row 갯수 : {0}", update));
        }
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
                UPDATE_NAME_BY_ID_QUERY, targetCustomer.name(), UuidUtils.uuidToBytes(targetCustomer.customerId()));
        if (update != 1) {
            throw new DataIntegrityViolationException(MessageFormat.format("데이터 업데이트 실패, 유효 row 갯수 {0}", update));
        }
    }

    private List<Customer> mapToCustomer(ResultSet resultSet) {
        List<Customer> customers = new ArrayList<>();
        Map<UUID, Customer> customerMap = new HashMap<>();
        try {
            while (resultSet.next()) {
                var customerId = UuidUtils.bytesToUUID(resultSet.getBytes("customer_id"));
                var name = resultSet.getString("name");
                var email = resultSet.getString("email");
                if (!customerMap.containsKey(customerId))
                    customerMap.put(customerId, new Customer(customerId, name, email, new ArrayList<>()));
                var voucherIdBytes = resultSet.getBytes("voucher_id");
                if (voucherIdBytes == null) {
                    continue;
                }
                var voucherId = UuidUtils.bytesToUUID(voucherIdBytes);
                customerMap.get(customerId).addVoucher(voucherRepository.findById(voucherId).orElseThrow());
            }
        } catch (SQLException e) {
            throw new DataRetrievalFailureException(MessageFormat.format("데이터를 가져오는 데 실패했습니다. {0}", e.getMessage()));
        }
        return new ArrayList<>(customerMap.values());
    }

}
