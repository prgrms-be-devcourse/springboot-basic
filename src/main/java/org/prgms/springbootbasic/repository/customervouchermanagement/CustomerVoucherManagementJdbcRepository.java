package org.prgms.springbootbasic.repository.customervouchermanagement;

import org.prgms.springbootbasic.common.UtilMethod;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static org.prgms.springbootbasic.common.UtilMethod.bytesToUUID;

@Repository
public class CustomerVoucherManagementJdbcRepository implements CustomerVoucherManagementRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerVoucherManagementJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public void allocateVoucherById(UUID customerId, UUID voucherId) {
        jdbcTemplate.update("INSERT INTO customers_vouchers VALUES (UNHEX(REPLACE(:customerId, '-', '')), UNHEX(REPLACE(:voucherId, '-', '')))",
                toParamMap(customerId, voucherId));
    }

    @Override
    public void deleteVoucherById(UUID customerId, UUID voucherId) {
        jdbcTemplate.update("DELETE FROM customers_vouchers " +
                        "WHERE customer_id = UNHEX(REPLACE(:customerId, '-', '')) AND voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(customerId, voucherId));
    }

    @Override
    public List<Voucher> searchVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT v.voucher_id, v.discount_degree, v.voucher_type " +
                        "FROM vouchers v JOIN customers_vouchers w ON v.voucher_id = w.voucher_id " +
                        "WHERE w.customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                mapToVoucher);
    }

    @Override
    public List<Customer> searchCustomersByVoucherId(UUID voucherId) {
        return jdbcTemplate.query("SELECT c.customer_id, c.name, c.email, c.last_login_at, c.created_at, c.is_blacked " +
                        "FROM customers c " +
                        "JOIN customers_vouchers w ON c.customer_id = w.customer_id " +
                        "WHERE w.voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                mapToCustomer);
    }

    private Map<String, Object> toParamMap(UUID customerId, UUID voucherId){
        return new HashMap<>(){{
            put("customerId", customerId.toString().getBytes());
            put("voucherId", voucherId.toString().getBytes());
        }};
    }

    private static RowMapper<Voucher> mapToVoucher = (rs, rowNum) -> {
        UUID voucherId = bytesToUUID(rs.getBytes("voucher_id"));
        long discountDegree = rs.getLong("discount_degree");
        String voucherTypeString = rs.getString("voucher_type");
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(vt -> vt.getDisplayName().equals(voucherTypeString))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 VoucherType이 존재하지 않음."));
        VoucherPolicy voucherPolicy = voucherType.create();

        return new Voucher(voucherId, discountDegree, voucherPolicy);
    };

    private static RowMapper<Customer> mapToCustomer = (rs, rowNum) -> {
        String customerName = rs.getString("name");
        UUID customerId = UtilMethod.bytesToUUID(rs.getBytes("customer_id"));
        String email = rs.getString("email");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at") != null
                ? rs.getTimestamp("last_login_at").toLocalDateTime() : null;
        boolean isBlacked = rs.getBoolean("is_blacked");

        return new Customer(customerId, customerName, email, lastLoginAt, createdAt, isBlacked);
    }; // static 메서드의 위치 조정.

}
