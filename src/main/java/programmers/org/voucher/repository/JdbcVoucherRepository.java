package programmers.org.voucher.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import programmers.org.voucher.domain.constant.VoucherType;
import programmers.org.voucher.domain.Voucher;
import programmers.org.voucher.dto.VoucherRequest;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcVoucherRepository implements VoucherRepository {

    private static final String INSERT = "INSERT INTO vouchers(discount_amount, type) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM vouchers";
    private static final String UPDATE = "UPDATE vouchers SET discount_amount=? WHERE voucher_id=?";
    private static final String SELECT_BY_ID = "SELECT * FROM vouchers WHERE voucher_id=?";
    private static final String DELETE_BY_ID = "DELETE FROM vouchers WHERE voucher_id=?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT, new String[]{"id"});
            statement.setInt(1, voucher.getDiscountAmount());
            statement.setString(2, voucher.getType().toString());
            return statement;
        }, keyHolder);
    }

    @Override
    public List<Voucher> getAll() {
        List<Voucher> voucherList = new ArrayList<>();
        return jdbcTemplate.query(SELECT_ALL, voucherRowMapper(), voucherList.toArray());
    }

    @Override
    public void update(Long id, VoucherRequest request) {
        jdbcTemplate.update(UPDATE, request.getDiscountAmount(), id);
    }

    @Override
    public Optional<Voucher> findById(Long id) {
        try {
            Voucher voucher = jdbcTemplate.queryForObject(SELECT_BY_ID, voucherRowMapper(), id);
            return Optional.of(voucher);

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (resultSet, rowNum) -> {
            long id = resultSet.getLong("voucher_id");
            int discountAmount = resultSet.getInt("discount_amount");
            VoucherType type = VoucherType.find(resultSet.getString("type"));

            return new Voucher(id, discountAmount, type);
        };
    }
}
