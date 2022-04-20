package org.programmer.kdtspringboot.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class WalletJdbcTemplateRepository implements WalletRepository{

    private static final Logger logger = LoggerFactory.getLogger(WalletJdbcTemplateRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Wallet insert(Wallet wallet) {
        return null;
    }

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        return null;
    }

    @Override
    public List<Wallet> findAll() {
        return null;
    }

    @Override
    public void deleteVoucher(Wallet wallet) {

    }
}
