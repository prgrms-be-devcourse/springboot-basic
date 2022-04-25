package org.prgms.springbootbasic.customer.repository.customer;

import static com.google.common.base.Preconditions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgms.springbootbasic.customer.entity.Customer;
import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.prgms.springbootbasic.customer.repository.customerstatus.CustomerStatusRepository;
import org.prgms.springbootbasic.util.UUIDConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcCustomerRepository implements CustomerRepository {

	private final JdbcTemplate jdbcTemplate;
	private final CustomerStatusRepository customerStatusRepository;

	@Override
	public UUID save(Customer customer) {
		checkArgument(customer != null, "customer는 null 이면 안됩니다.");

		final String SAVE_TYPE_SQL = "insert into customer (customer_id, customer_status_id, name, email) values (?, ?, ?,?)";

		final byte[] customerId = UUIDConverter.uuidToBytes(customer.getCustomerId());
		final byte[] customerStatusId = UUIDConverter.uuidToBytes(
			customerStatusRepository.findUUIDByName(customer.getStatus().name()));

		jdbcTemplate.update(SAVE_TYPE_SQL, customerId, customerStatusId, customer.getName(), customer.getEmail());

		return customer.getCustomerId();
	}

	@Override
	public Customer findById(UUID customerId) {
		final String FIND_BY_ID_SQL = "select * from customer where customer_id = ?";

		final byte[] customerIdBytes = UUIDConverter.uuidToBytes(customerId);

		return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, customerRowMapper(),
			customerIdBytes);
	}

	private RowMapper<Customer> customerRowMapper() {
		return (rs, rowNum) -> {
			final UUID customerId = UUIDConverter.bytesToUUID(rs.getBytes("customer_id"));
			final UUID customerStatusId = UUIDConverter.bytesToUUID(rs.getBytes("customer_status_id"));
			final String name = rs.getString("name");
			final String email = rs.getString("email");

			final CustomerStatus customerStatus = customerStatusRepository.findById(customerStatusId);

			return new Customer(customerId, name, email, customerStatus);
		};
	}

	@Override
	public Map<CustomerStatus, List<Customer>> getCustomerListByStatus() {
		final String FIND_CUSTOMER_LIST_SQL = "select * from customer";

		List<Customer> customers = jdbcTemplate.query(FIND_CUSTOMER_LIST_SQL, customerRowMapper());

		return customers.stream()
			.collect(Collectors.groupingBy((customer) -> customer.getStatus()));
	}

	@Override
	public int getTotalCustomerCount() {
		final String FIND_COUNT_SQL = "select count(*) from customer";

		return jdbcTemplate.queryForObject(FIND_COUNT_SQL, Integer.class);
	}

	@Override
	public void deleteCustomers() {
		final String DELETE_SQL = "delete from customer";

		jdbcTemplate.update(DELETE_SQL);
	}
}
