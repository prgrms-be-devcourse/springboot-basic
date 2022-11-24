package org.prgrms.kdtspringdemo.domain.voucher.repository;

import org.prgrms.kdtspringdemo.domain.voucher.VoucherCreator;
import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.VoucherManagerUtil.toUUID;

@Repository
@Profile("db")
public class VoucherNamedJdbcRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherNamedJdbcRepository.class);
    private static final String INSERT_SQL =
            "INSERT INTO vouchers_demo (voucher_id, voucher_type, value) VALUES (UUID_TO_BIN(:voucherId),:voucherType,:value);";
    private static final String FIND_BY_ID_SQL =
            "SELECT v.voucher_id , vt.voucher_type , v.value  FROM vouchers_demo v JOIN voucher_type vt on vt.id = v.voucher_type WHERE voucher_id = UUID_TO_BIN(:voucherId);";
    private static final String FIND_ALL_SQL =
            "SELECT v.voucher_id, vt.voucher_type, v.value  FROM vouchers_demo v JOIN voucher_type vt on vt.id = v.voucher_type;";
    //    private static final String UPDATE_SQL = "";
    private static final String DELETE_SQL = "DELETE FROM vouchers_demo WHERE voucher_id = UUID_TO_BIN(:voucherId);";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers_demo";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final VoucherCreator voucherCreator;
    private final RowMapper<Voucher> rowMapper;

    public VoucherNamedJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, VoucherCreator voucherCreator) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.voucherCreator = voucherCreator;
        this.rowMapper = (resultSet, i) -> {
            var voucherId = toUUID(resultSet.getBytes("voucher_id"));
            var voucherType = VoucherType.getTypeByName(resultSet.getString("voucher_type"));
            var value = resultSet.getLong("value");
            var voucher = this.voucherCreator.createVoucher(voucherId, voucherType, value);
            return voucher;
        };
    }

    @Transactional
    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            namedParameterJdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
            return Optional.of(voucher);
        } catch (DataAccessException e) {
            logger.error("중복되는 ID 입니다.", e);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            FIND_BY_ID_SQL,
                            Collections.singletonMap("voucherId", voucherId.toString()),
                            rowMapper
                    )
            );
        } catch (DataAccessException e) {
            logger.error("ID로부터 찾아올 수 없습니다.", e);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public List<Voucher> findAllVaucher() {
        return namedParameterJdbcTemplate.query(FIND_ALL_SQL, rowMapper);
    }

    @Transactional
    @Override
    public void deleteById(UUID voucherId) {
        namedParameterJdbcTemplate.update(DELETE_SQL, Collections.singletonMap("voucherId", voucherId.toString()));
    }

    @Transactional
    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        var voucherTypeId = findVoucherTypeId(voucher.getVoucherType()).get();
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString());
            put("voucherType", voucherTypeId);
            put("value", voucher.getValue());
        }};
    }

    private Optional<Integer> findVoucherTypeId(VoucherType voucherType) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcTemplate.queryForObject(
                            "SELECT id FROM voucher_type WHERE voucher_type = :voucherType",
                            Collections.singletonMap("voucherType", voucherType.name()),
                            Integer.class
                    )
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
