package org.prgrms.orderApp.voucher;

import lombok.AllArgsConstructor;
import org.prgrms.orderApp.common.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;


@Repository
@AllArgsConstructor
public class VoucherNameJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherNameJdbcRepository.class);

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;



    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String SELECT_BY_ID_SQL = """
                                    SELECT v.voucher_id, v.amount,v.voucher_type,v.created_at,v.expire_at,v.available,v.name, count(va.voucher_id) AS count
                                    FROM vouchers v
                                    LEFT JOIN vouchers_assigned va
                                    ON v.voucher_id = va.voucher_id AND v.available = 'T'
                                    WHERE v.voucher_id=UUID_TO_BIN(:voucherId)
                                    GROUP BY v.voucher_id                                       
                                """;
        return jdbcTemplate.query(SELECT_BY_ID_SQL,Collections.singletonMap("voucherId", voucherId.toString().getBytes()), (resultSet, i) -> mapToCustomerByJdbcTemplate(resultSet)).stream().findFirst();
    }


    @Override
    public int insert(Voucher voucher) {
        String INSERT_SQL = """
                                 INSERT INTO vouchers(voucher_id, amount, voucher_type, name, expire_at, available) 
                                 VALUE (UUID_TO_BIN(:voucherId),:amount,:voucherType,:name,:expireAt, :available)
                            """;
        return jdbcTemplate.update( INSERT_SQL, toParameter(voucher));
    }


    @Override
    public List<Voucher> findAll() {
        String SELECT_ALL_SQL = """
                                    SELECT v.voucher_id, v.amount,v.voucher_type,v.created_at,v.expire_at,v.available,v.name, count(va.voucher_id) AS count
                                    FROM vouchers v
                                    LEFT JOIN vouchers_assigned va
                                    ON v.voucher_id = va.voucher_id AND v.available = 'T' 
                                    GROUP BY v.voucher_id
                                """;
        return jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, i)-> mapToCustomerByJdbcTemplate(resultSet));
    }


    @Override
    public int deleteById(UUID voucherId) {
        String DELETE_BY_ID_FROM_VOUCHERS_ASSIGNED_SQL = """
                                     DELETE v, va
                                     FROM vouchers AS v
                                     INNER JOIN vouchers_assigned AS va
                                     ON v.voucher_id = va.voucher_id
                                     WHERE v.voucher_id=UUID_TO_BIN(:voucherId)
                                 """;
        String DELETE_BY_ID_FROM_VOUCHERS_SQL = """
                                    DELETE 
                                    FROM vouchers 
                                    WHERE voucher_id=UUID_TO_BIN(:voucherId)
                                """;
        var resultFromVouchersAssigned = jdbcTemplate.update(DELETE_BY_ID_FROM_VOUCHERS_ASSIGNED_SQL, toParameter(new Voucher(voucherId)));
        var resultFromVouchers = jdbcTemplate.update(DELETE_BY_ID_FROM_VOUCHERS_SQL, toParameter(new Voucher(voucherId)));
        return resultFromVouchers + resultFromVouchersAssigned;
    }


    @Override
    public List<Voucher> findAllByType(String voucherType){
        String SELECT_BY_TYPE_SQL = """
                                    SELECT v.voucher_id, v.amount,v.voucher_type,v.created_at,v.expire_at,v.available,v.name, count(va.voucher_id) AS count
                                    FROM vouchers v
                                    LEFT JOIN vouchers_assigned va
                                    ON v.voucher_id = va.voucher_id AND v.available = 'T'
                                    WHERE v.voucher_type = :voucher_type
                                    GROUP BY v.voucher_id   
                                    """;
        return jdbcTemplate.query(SELECT_BY_TYPE_SQL, Collections.singletonMap("voucher_type", voucherType), (resultSet, i)-> mapToCustomerByJdbcTemplate(resultSet));
    }


    @Override
    public List<Voucher> findAllByCreatedDate(LocalDateTime fromDate, LocalDateTime toDate) {
        String SELECT_BY_CREATEDAT_SQL = """
                                        SELECT v.voucher_id, v.amount,v.voucher_type,v.created_at,v.expire_at,v.available,v.name, count(va.voucher_id) AS count
                                        FROM vouchers v
                                        LEFT JOIN vouchers_assigned va
                                        ON v.voucher_id = va.voucher_id AND v.available = 'T'
                                        WHERE DATE(created_at) BETWEEN :from_date AND :to_date
                                        GROUP BY v.voucher_id
                                    """;



        return jdbcTemplate.query(SELECT_BY_CREATEDAT_SQL, Map.of("from_date",fromDate, "to_date",toDate), (resultSet, i)-> mapToCustomerByJdbcTemplate(resultSet));
    }



    private Map<String, Object> toParameter(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("amount", voucher.getAmount());
            put("available", voucher.getAvailable());
            put("voucherType", voucher.getVoucherType());
            put("name", voucher.getName());
            put("expireAt", voucher.getExpireAt() != null ? Timestamp.valueOf(voucher.getExpireAt()) : null);
            put("createdAt", voucher.getCreatedAt() != null ? Timestamp.valueOf(voucher.getCreatedAt()) : null);
        }};
    }


    private Voucher mapToCustomerByJdbcTemplate(ResultSet resultSet) throws SQLException {
        return new Voucher(
                Common.toUUID(resultSet.getBytes("voucher_id")),
                resultSet.getLong("amount"),
                resultSet.getString("available"),
                resultSet.getString("voucher_type"),
                resultSet.getString("name"),
                resultSet.getTimestamp("expire_at").toLocalDateTime(),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                resultSet.getInt("count")
        );
    }
}
