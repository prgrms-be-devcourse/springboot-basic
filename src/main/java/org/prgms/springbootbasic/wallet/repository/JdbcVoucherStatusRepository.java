package org.prgms.springbootbasic.wallet.repository;

import java.util.UUID;

import org.prgms.springbootbasic.customer.entity.CustomerStatus;
import org.prgms.springbootbasic.customer.repository.customerstatus.CustomerStatusRepository;
import org.prgms.springbootbasic.util.UUIDConverter;
import org.prgms.springbootbasic.wallet.entity.VoucherStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcVoucherStatusRepository implements VoucherStatusRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public VoucherStatus findById(UUID statusId) {
		final String FIND_UUID_NY_NAME_SQL = "select * from voucher_status where voucher_status_id = ?";
		return jdbcTemplate.queryForObject(FIND_UUID_NY_NAME_SQL, voucherStatusMapper(),
			UUIDConverter.uuidToBytes(statusId));
	}

	private RowMapper<VoucherStatus> voucherStatusMapper() {
		return (rs, rowNum) -> {
			return VoucherStatus.of(rs.getString("status"));
		};
	}

	@Override
	public UUID findUUIDByName(String statusName) {
		final String FIND_UUID_NY_NAME_SQL = "select voucher_status_id from voucher_status where status = ?";
		return jdbcTemplate.queryForObject(FIND_UUID_NY_NAME_SQL, uuidRowMapper(), statusName);
	}

	private RowMapper<UUID> uuidRowMapper() {
		return (rs, rowNum) -> {
			return UUIDConverter.bytesToUUID(rs.getBytes("voucher_status_id"));
		};
	}
}
