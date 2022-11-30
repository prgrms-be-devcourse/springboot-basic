package com.programmers.kwonjoosung.springbootbasicjoosung.repository.wallet;


import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataAlreadyExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.exception.DataNotExistException;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.CustomerTable.CUSTOMER_ID;
import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.VoucherTable.VOUCHER_ID;
import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.customer.JdbcCustomerRepository.customerRowMapper;
import static com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher.JdbcVoucherRepository.voucherRowMapper;


@Repository
public class JdbcWalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String WALLET = "wallet";
    private static final int FAIL = 0;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertToWallet(UUID customerId, UUID voucherId) {
        final String sql = "INSERT INTO wallets (customer_id, voucher_id) VALUES (:customer_id, :voucher_id)";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getColumnName(), customerId.toString())
                .addValue(VOUCHER_ID.getColumnName(), voucherId.toString());
        try {
            jdbcTemplate.update(sql, parameters);
        } catch (DuplicateKeyException e) {
            throw new DataAlreadyExistException(voucherId.toString(), WALLET);
        } catch (DataIntegrityViolationException e) {
            throw new DataNotExistException();
        }
    }

    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        final String sql = "SELECT * FROM wallets W JOIN vouchers V ON V.voucher_id = W.voucher_id WHERE customer_id = :customer_id ";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID.getColumnName(), customerId.toString());
        try {
            return jdbcTemplate.query(sql, parameter, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    public Customer findCustomerByVoucherId(UUID voucherId) {
        final String sql = "SELECT * FROM wallets W JOIN customers C on C.customer_id = W.customer_id WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(VOUCHER_ID.getColumnName(), voucherId.toString());
        try {
            return jdbcTemplate.queryForObject(sql, parameter, customerRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotExistException(voucherId.toString(), WALLET);
        }
    }

    public void deleteVoucher(UUID voucherId) {
        final String sql = "DELETE FROM wallets WHERE voucher_id = :voucher_id";
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(VOUCHER_ID.getColumnName(), voucherId.toString());
        if (jdbcTemplate.update(sql, parameter) == FAIL) {
            throw new DataNotExistException(voucherId.toString(), WALLET);
        }
    }
}
