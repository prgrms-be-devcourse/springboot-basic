package org.prgms.springbootbasic.customer.repository.customerstatus;

import java.util.UUID;

import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.prgms.springbootbasic.util.UUIDConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcCustomerStatusRepository implements CustomerStatusRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public CustomerStatus findById(UUID statusId) {
		final String FIND_UUID_NY_NAME_SQL = "select * from customer_status where customer_status_id = ?";
		return jdbcTemplate.queryForObject(FIND_UUID_NY_NAME_SQL, customerStatusMapper(),
			UUIDConverter.uuidToBytes(statusId));
	}

	private RowMapper<CustomerStatus> customerStatusMapper() {
		return (rs, rowNum) -> {
			return CustomerStatus.of(rs.getString("status"));
		};
	}

	@Override
	public UUID findUUIDByName(String statusName) {
		final String FIND_UUID_NY_NAME_SQL = "select customer_status_id from customer_status where status = ?";
		return jdbcTemplate.queryForObject(FIND_UUID_NY_NAME_SQL, uuidRowMapper(), statusName);
	}

	private RowMapper<UUID> uuidRowMapper() {
		return (rs, rowNum) -> {
			return UUIDConverter.bytesToUUID(rs.getBytes("customer_status_id"));
		};
	}
}
