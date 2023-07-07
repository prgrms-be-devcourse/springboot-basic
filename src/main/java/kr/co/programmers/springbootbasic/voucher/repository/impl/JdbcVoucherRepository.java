package kr.co.programmers.springbootbasic.voucher.repository.impl;

import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.PercentAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.exception.JdbcVoucherRepositoryFailException;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherQuery;
import kr.co.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
@Profile({"deploy", "dev", "test"})
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher create(Voucher voucher) {
        jdbcTemplate.update(VoucherQuery.CREATE_VOUCHER,
                voucher.getId().toString().getBytes(),
                voucher.getType().getTypeId(),
                voucher.getAmount(),
                voucher.getWalletId());

        return findVoucherById(voucher.getId())
                .orElseThrow(() -> new JdbcVoucherRepositoryFailException("바우처를 저장하는데 실패했습니다."));
    }

    @Override
    public Optional<Voucher> findVoucherById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(VoucherQuery.FIND_VOUCHER_BY_ID,
                    voucherRowMapper(),
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.info("voucher id : {}가 존재하지 않습니다.", voucherId);

            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> listAll() {
        return jdbcTemplate.query(VoucherQuery.LIST_ALL,
                voucherRowMapper());
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(VoucherQuery.DELETE_BY_ID,
                voucherId.toString().getBytes());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            var voucherId = ApplicationUtils.toUUID(rs.getBytes("id"));
            var type = VoucherType.resolveTypeId(rs.getInt("type_id"));
            var amount = rs.getLong("amount");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return switch (type) {
                case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount, createdAt);
                case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount, createdAt);
            };
        };
    }
}
