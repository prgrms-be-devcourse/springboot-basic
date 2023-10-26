package com.programmers.vouchermanagement.repository.wallet;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.wallet.Wallet;
import com.programmers.vouchermanagement.dto.wallet.GetWalletsRequestDto;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import com.programmers.vouchermanagement.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcTemplateWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate template;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    @Autowired
    public JdbcTemplateWalletRepository(NamedParameterJdbcTemplate template, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.template = template;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void save(Wallet wallet) {
        String sql = "INSERT INTO wallets (customer_id, voucher_id, used) VALUES (:customerId, :voucherId, :used)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("customerId", wallet.getCustomer().getId().toString())
                .addValue("voucherId", wallet.getVoucher().getId().toString())
                .addValue("used", wallet.isUsed());

        template.update(sql, params);
    }

    @Override
    public void saveAll(List<Wallet> wallets) {
        String sql = "INSERT INTO wallets (customer_id, voucher_id, used) VALUES (:customerId, :voucherId, :used)";

        template.batchUpdate(sql, wallets.stream()
                .map(wallet -> new MapSqlParameterSource()
                        .addValue("customerId", wallet.getCustomer().getId().toString())
                        .addValue("voucherId", wallet.getVoucher().getId().toString())
                        .addValue("used", wallet.isUsed()))
                .toArray(SqlParameterSource[]::new));
    }

    @Override
    public Optional<Wallet> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Wallet> findAll(GetWalletsRequestDto request) {
        String sql = "SELECT * FROM wallets WHERE 1 = 1";

        if (request.getUsed() != null) {
            sql += "AND used = :used";
        }

        if (request.getCustomerId() != null) {
            sql += "AND customer_id = :customerId";
        }

        if (request.getVoucherId() != null) {
            sql += "AND voucher_id = :voucherId";
        }

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("used", request.getUsed())
                .addValue("customerId", request.getCustomerId().toString())
                .addValue("voucherId", request.getVoucherId().toString());

        return template.query(sql, params, getWalletRowMapper());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM wallets WHERE id = :id";
        template.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM wallets";
        template.update(sql, new MapSqlParameterSource());
    }

    private RowMapper<Wallet> getWalletRowMapper() {
        return (rs, rowNum) -> {
            int id = rs.getInt("id");
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            boolean used = rs.getBoolean("used");

            //! 고민
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new InternalError("Not found customer"));
            Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> new InternalError("Not found voucher"));

            return new Wallet(id, customer, voucher, used);
        };
    }
}
