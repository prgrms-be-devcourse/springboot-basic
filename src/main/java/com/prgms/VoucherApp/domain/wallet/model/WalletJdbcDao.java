package com.prgms.VoucherApp.domain.wallet.model;

import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.domain.voucher.model.Voucher;
import com.prgms.VoucherApp.domain.voucher.model.VoucherDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WalletJdbcDao implements WalletDao {
    private final Logger logger = LoggerFactory.getLogger(WalletJdbcDao.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final CustomerDao customerDao;
    private final VoucherDao voucherDao;

    public WalletJdbcDao(DataSource dataSource, CustomerDao customerDao, VoucherDao voucherDao) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.customerDao = customerDao;
        this.voucherDao = voucherDao;
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "INSERT INTO wallet VALUES (:id, :customerId, :voucherId)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", wallet.getWalletId())
            .addValue("customerId", wallet.getCustomer().getCustomerId())
            .addValue("voucherId", wallet.getVoucher().getVoucherId());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count != 1) {
            logger.warn("지갑이 생성되지 않은 예외가 발생 입력 값 {}", wallet);
            throw new IllegalArgumentException("입력 값의 문제로 지갑이 생성되지 못했습니다.");
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        String sql = "SELECT * FROM wallet WHERE id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", walletId);
        try {
            Wallet wallet = namedParameterJdbcTemplate.queryForObject(sql, paramMap, walletRowMapper());
            return Optional.of(wallet);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("존재하지 않는 아이디가 입력되어 조회하지 못하는 예외가 발생 id = {}", walletId);
            throw new IllegalArgumentException("존재하지 않는 아이디가 입력되었습니다.", exception);
        }
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM wallet WHERE customer_id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", customerId);
        List<Wallet> wallets = namedParameterJdbcTemplate.query(sql, paramMap, walletRowMapper());
        return wallets;
    }

    @Override
    public Optional<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM wallet WHERE voucher_id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucherId);

        try {
            Wallet wallet = namedParameterJdbcTemplate.queryForObject(sql, paramMap, walletRowMapper());
            return Optional.of(wallet);
        } catch (EmptyResultDataAccessException exception) {
            logger.warn("존재하지 않는 아이디가 입력되어 조회하지 못하는 예외가 발생 id = {}", voucherId);
            throw new IllegalArgumentException("존재하지 않는 아이디가 입력되었습니다.", exception);
        }
    }

    @Override
    public void deleteById(UUID walletId) {
        String sql = "DELETE FROM wallet WHERE id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", walletId);

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.warn("존재하지 않는 아이디가 입력되어 조회하지 못하는 예외가 발생 id = {}", walletId);
            throw new IllegalArgumentException("존재하지 않는 id를 입력 받았습니다.");
        }
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            UUID walletId = UUID.fromString(id);
            Voucher voucher = getVoucher(resultSet);
            Customer customer = getCustomer(resultSet);
            return new Wallet(walletId, customer, voucher);
        };
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        String findCustomerId = resultSet.getString("customer_id");
        UUID customerId = UUID.fromString(findCustomerId);
        return customerDao.findById(customerId).get();
    }

    private Voucher getVoucher(ResultSet resultSet) throws SQLException {
        String findVoucherId = resultSet.getString("voucher_id");
        UUID voucherId = UUID.fromString(findVoucherId);
        return voucherDao.findById(voucherId).get();
    }
}
