package org.prgrms.kdt.model.repository.jdbc;

import java.util.List;
import java.util.Optional;

import org.prgrms.kdt.common.codes.ErrorCode;
import org.prgrms.kdt.common.exception.VoucherRuntimeException;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.model.repository.VoucherRepository;
import org.prgrms.kdt.model.repository.file.FileVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@Qualifier("JdbcVoucherRepository")
public class VoucherJdbcRepository implements VoucherRepository {

	private final JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	private static RowMapper<VoucherEntity> voucherEntityRowMapper = (resultSet, i) -> {
		Long voucherId = resultSet.getLong("voucher_id");
		int amount = resultSet.getInt("amount");
		String voucherTypeString = resultSet.getString("voucher_type");
		return new VoucherEntity(voucherId, amount, voucherTypeString);
	};

	public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public VoucherEntity saveVoucher(VoucherEntity voucherEntity) {
		try {
			jdbcTemplate.update(
				"INSERT INTO vouchers(voucher_id, amount, voucher_type) VALUES (?, ?, ?)",
				voucherEntity.getVoucherId(),
				voucherEntity.getAmount(),
				voucherEntity.getVoucherType()
			);
			return voucherEntity;
		} catch (DataAccessException e) {
			logger.error("voucher entity id is {}", voucherEntity.getVoucherId());
			logger.error(ErrorCode.VOUCHER_CREATE_FAIL.getErrorMessage(), e);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_CREATE_FAIL);
		}
	}

	@Override
	public List<VoucherEntity> findAllEntities() {
		return jdbcTemplate.query("select * from vouchers", voucherEntityRowMapper);
	}

	@Override
	public VoucherEntity updateVoucher(VoucherEntity voucherEntity) {

		try {
			jdbcTemplate.update("UPDATE  vouchers SET amount = ?, voucher_type = ? WHERE voucher_id = ?",
				voucherEntity.getAmount(),
				voucherEntity.getVoucherType().toString(),
				voucherEntity.getVoucherId()
			);
			return voucherEntity;
		} catch (DataAccessException e) {
			logger.error("voucher entity id is {}", voucherEntity.getVoucherId());
			logger.error(ErrorCode.VOUCHER_UPDATE_FAIL.getErrorMessage(), e);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_UPDATE_FAIL);
		}
	}

	@Override
	public Optional<VoucherEntity> findVoucherById(Long voucherId) {
		try {
			VoucherEntity voucherEntity = jdbcTemplate.queryForObject(
				"select * from vouchers WHERE voucher_id = ?",
				voucherEntityRowMapper,
				voucherId);
			return Optional.ofNullable(voucherEntity);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void deleteVoucherById(Long voucherId) {
		try {
			jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = ?", voucherId);
		} catch (DataAccessException e) {
			logger.error("voucher entity id is {}", voucherId);
			logger.error(ErrorCode.VOUCHER_DELETE_FAIL.getErrorMessage(), e);
			throw new VoucherRuntimeException(ErrorCode.VOUCHER_DELETE_FAIL);
		}
	}
}
