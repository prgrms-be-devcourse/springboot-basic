package org.prgms.springbootbasic.voucher.repository.vouchertype;

import java.util.UUID;

import org.prgms.springbootbasic.util.UUIDConverter;
import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * VoucherType은 FIXEDAMOUNTVOUCHER, PERCENTDISCOUNTVOUCHER로 미리 데이터베이스에 생성 해 두었습니다!
 * VoucherType 조회용 Repository
 */
@Repository
@RequiredArgsConstructor
public class JdbcVoucherTypeRepository implements VoucherTypeRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public VoucherType save(VoucherType voucherType) {
		final String SAVE_TYPE_SQL = "insert into voucher_type (voucher_type_id, name) values (?, ?)";
		final UUID uuid = UUID.randomUUID();
		System.out.println(uuid);
		jdbcTemplate.update(SAVE_TYPE_SQL, UUIDConverter.uuidToBytes(uuid), voucherType.name());
		return voucherType;
	}

	/**
	 * Voucher 이름을 입력받으면 해당 Voucher_type_id를 반환
	 * Voucher의 외래키로 Voucher_type_id를 넣어주는 데 사용하기 위한 용도로 정의
	 *
	 * @param voucherTypeName
	 * @return UUID : voucher_type_id
	 */
	@Override
	public UUID findUUIDByName(String voucherTypeName) {
		final String FIND_UUID_NY_NAME_SQL = "select voucher_type_id from voucher_type where name = ?";
		return jdbcTemplate.queryForObject(FIND_UUID_NY_NAME_SQL, uuidRowMapper(), voucherTypeName);
	}

	private RowMapper<UUID> uuidRowMapper() {
		return (rs, rowNum) -> {
			return UUIDConverter.bytesToUUID(rs.getBytes("voucher_type_id"));
		};
	}

	/**
	 * voucher_type_id를 입력받으면 VoucherType을 반환해주는 메서드
	 *
	 * @param voucherId
	 * @return VoucherType
	 */
	@Override
	public VoucherType findById(UUID voucherId) {
		final String FIND_UUID_NY_NAME_SQL = "select * from voucher_type where voucher_type_id = ?";
		System.out.println(voucherId);
		return jdbcTemplate.queryForObject(FIND_UUID_NY_NAME_SQL, voucherTypeRowMapper(),
			UUIDConverter.uuidToBytes(voucherId));
	}

	private RowMapper<VoucherType> voucherTypeRowMapper() {
		return (rs, rowNum) -> {
			return VoucherType.of(rs.getString("name"));
		};
	}
}
