package org.promgrammers.springbootbasic.domain.wallet.repository.impl;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.JdbcCustomerRepository;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.promgrammers.springbootbasic.domain.wallet.model.Wallet;
import org.promgrammers.springbootbasic.domain.wallet.repository.WalletRepository;
import org.promgrammers.springbootbasic.exception.repository.NonExistentDomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class JdbcWalletRepository implements WalletRepository {

    private final JdbcCustomerRepository customerRepository;
    private final JdbcVoucherRepository voucherRepository;
    private final NamedParameterJdbcTemplate template;

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    private static final String SAVE = "INSERT INTO wallet (wallet_id, voucher_id, customer_id) VALUES (:walletId, :voucherId, :customerId)";
    private static final String FIND_ALL = "SELECT * FROM wallet";
    private static final String FIND_BY_ID = "SELECT * FROM wallet WHERE wallet_id = :walletId";
    private static final String FIND_ALL_BY_VOUCHER_ID = "SELECT * FROM wallet WHERE voucher_id = :voucherId";
    private static final String FIND_ALL_BY_CUSTOMER_ID = "SELECT * FROM wallet WHERE customer_id = :customerId";
    private static final String DELETE_ALL = "DELETE FROM wallet";
    private static final String DELETE_BY_ID = "DELETE FROM wallet WHERE wallet_id = :walletId";


    public JdbcWalletRepository(JdbcCustomerRepository customerRepository, JdbcVoucherRepository voucherRepository, DataSource dataSource) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }


    private SqlParameterSource createParameterSource(Wallet wallet) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("walletId", wallet.getWalletId().toString())
                .addValue("voucherId", wallet.getVoucher().getVoucherId().toString())
                .addValue("customerId", wallet.getCustomer().getCustomerId().toString());
        return paramSource;
    }

    @Override
    public Wallet save(Wallet wallet) {
        SqlParameterSource parameterSource = createParameterSource(wallet);
        int saveCount = template.update(SAVE, parameterSource);

        if (saveCount != 1) {
            throw new DataAccessException("지갑 저장에 실패 했습니다. => " + wallet.getWalletId()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return wallet;
    }

    @Override
    public List<Wallet> findAll() {
        return template.query(FIND_ALL, walletRowMapper());
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        try {
            Map<String, Object> param = Map.of("walletId", walletId);
            Wallet wallet = template.queryForObject(FIND_BY_ID, param, walletRowMapper());
            return Optional.ofNullable(wallet);
        } catch (EmptyResultDataAccessException e) {
            logger.error("해당 ID를 찾을 수 없습니다. => " + walletId);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findAllWalletByCustomerId(UUID customerId) {
        Map<String, Object> params = Collections.singletonMap("customerId", customerId.toString());
        return template.query(FIND_ALL_BY_CUSTOMER_ID, params, walletRowMapper());
    }

    @Override
    public List<Wallet> findAllWalletByVoucherId(UUID voucherId) {
        Map<String, Object> params = Collections.singletonMap("voucherId", voucherId.toString());
        return template.query(FIND_ALL_BY_VOUCHER_ID, params, walletRowMapper());
    }

    @Override
    public void deleteAll() {
        template.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("walletId", id.toString());

        int deleteCount = template.update(DELETE_BY_ID, params);
        if (deleteCount == 0) {
            throw new NonExistentDomainException("해당 아이디가 존재하지 않습니다. => " + id);
        }

        template.update(DELETE_BY_ID, params);
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            UUID walletId = UUID.fromString(rs.getString("wallet_id"));
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            UUID customerId = UUID.fromString(rs.getString("customer_id"));

            Voucher voucher = voucherRepository.getById(voucherId);
            Customer customer = customerRepository.getById(customerId);

            return new Wallet(walletId, voucher, customer);
        };
    }
}
