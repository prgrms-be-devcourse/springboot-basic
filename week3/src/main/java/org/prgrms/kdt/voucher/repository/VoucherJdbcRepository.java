package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherGenerator;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.voucher.util.Util.toUUID;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("amount");
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var walletId = toUUID(resultSet.getBytes("wallet_id"));

        var voucher = VoucherGenerator.createVoucher(voucherId, amount, voucherType);
        voucher.setWalletId(walletId);
        return voucher;
    };

    @Override
    public int insert(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, amount, voucher_type) values(UUID_TO_BIN(?), ? , ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getAmount(),
                voucher.getVoucherType());
        return update;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    public void deleteAll(){
        jdbcTemplate.update("DELETE FROM vouchers");
    }

    public List<Voucher> findByWalletId(UUID walletId){
        return (jdbcTemplate.query(
                "select * from voucher where wallet_id = ?",
                voucherRowMapper,
                walletId.toString().getBytes()));
    }

    //지갑에 있는 모든 바우처 제거
    public void deleteByWalletId(UUID walletId){
        jdbcTemplate.update("delete from voucher where wallet_id = ?", walletId.toString().getBytes());
    }

    public List<Voucher> findByVoucherType(VoucherType voucherType){
        return (jdbcTemplate.query(
                "select * from voucher where voucher_type = ?",
                voucherRowMapper,
                voucherType));
    }

    public int insertWalletIdToVoucher(UUID walletId, Voucher voucher){
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, amount, voucher_type, wallet_id) values(UUID_TO_BIN(?), ? , ?, UUID_TO_BIN(?))",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getAmount(),
                voucher.getVoucherType(),
                walletId.toString().getBytes());
        return update;
    }
}
