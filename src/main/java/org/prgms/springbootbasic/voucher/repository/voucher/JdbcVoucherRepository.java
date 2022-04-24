package org.prgms.springbootbasic.voucher.repository.voucher;

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgms.springbootbasic.util.UUIDConverter;
import org.prgms.springbootbasic.voucher.repository.vouchertype.VoucherTypeRepository;
import org.prgms.springbootbasic.voucher.vo.Voucher;
import org.prgms.springbootbasic.voucher.vo.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

import lombok.RequiredArgsConstructor;

@Repository
@Primary
@RequiredArgsConstructor
public class JdbcVoucherRepository implements VoucherRepository {
	private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

	private final JdbcTemplate jdbcTemplate;
	private final VoucherTypeRepository voucherTypeRepository;

	@Override
	public UUID save(Voucher voucher) {
		checkArgument(voucher != null, "voucher는 null 이면 안됩니다.");

		final String SAVE_TYPE_SQL = "insert into voucher (voucher_id, voucher_type_id, value) values (?, ?, ?)";

		final byte[] voucherId = UUIDConverter.uuidToBytes(voucher.getVoucherId());
		final byte[] voucherTypeId = UUIDConverter.uuidToBytes(voucherTypeRepository.findUUIDByName(voucher.getType().name()));

		jdbcTemplate.update(SAVE_TYPE_SQL, voucherId, voucherTypeId, voucher.getValue());

		return voucher.getVoucherId();
	}

	@Override
	public Voucher findById(UUID voucherId) {
		final String FIND_BY_ID_SQL = "select * from voucher where voucher_id = ?";

		final byte[] voucherIdBytes = UUIDConverter.uuidToBytes(voucherId);

		return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, voucherRowMapper(),
			voucherIdBytes);
	}

	private RowMapper<Voucher> voucherRowMapper() {
		return (rs, rowNum) -> {
			final UUID voucherId = UUIDConverter.bytesToUUID(rs.getBytes("voucher_id"));
			final UUID voucherTypeId = UUIDConverter.bytesToUUID(rs.getBytes("voucher_type_id"));
			final int value = rs.getInt("value");

			final VoucherType type = voucherTypeRepository.findById(voucherTypeId);

			return type.createVoucher(voucherId, type, value);
		};
	}

	@Override
	public Map<VoucherType, List<Voucher>> getVoucherListByType() {
		final String FIND_VOUCHER_LIST_SQL = "select * from voucher";

		List<Voucher> vouchers = jdbcTemplate.query(FIND_VOUCHER_LIST_SQL, voucherRowMapper());

		return vouchers.stream()
			.collect(Collectors.groupingBy( (voucher) -> voucher.getType()));
	}

	@Override
	public int getTotalVoucherCount() {
		final String FIND_COUNT_SQL = "select count(*) from voucher";

		return jdbcTemplate.queryForObject(FIND_COUNT_SQL,Integer.class);
	}

	@Override
	public void deleteVouchers() {
		final String DELETE_SQL = "delete from voucher";

		jdbcTemplate.update(DELETE_SQL);
	}

}
