package org.prgrms.kdt.repository.wallet;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.wallet.Wallet;
import org.prgrms.kdt.repository.customer.CustomerRepository;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Repository
public class JdbcWalletRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate, CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    @Override
    public Wallet insert(UUID customerId, UUID voucherId) {

        validateIsNotNull(customerId);
        validateIsNotNull(voucherId);
        validateDuplicate(customerId, voucherId);

        int insert = jdbcTemplate.update("INSERT INTO wallet (customer_id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?))", mapToBytes(customerId), mapToBytes(voucherId));

        if (insert != 1)
            throw new IllegalArgumentException(MessageFormat.format("Failed to insert voucher in wallet. customerId = {0}  voucherId = {1}", customerId, voucherId));


        return findByCustomerId(customerId);
    }


    @Override
    public Wallet findByCustomerId(UUID customerId) {

        validateIsNotNull(customerId);

        List<UUID> voucherIds = jdbcTemplate.query(
                "SELECT voucher_id FROM wallet WHERE customer_id = UUID_TO_BIN(?)",
                (resultSet, rowNum) -> {
                    UUID voucherId = mapToUUID(resultSet.getBytes("voucher_id"));
                    return voucherId;
                },
                mapToBytes(customerId)
        );

        return createWallet(customerId, voucherIds);
    }

    private Wallet createWallet(UUID customerId, List<UUID> voucherIds) {
        Wallet wallet = new Wallet(customerId);
        wallet.addVouchers(voucherIds);
        return wallet;
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {

        validateIsNotNull(voucherId);

        List<UUID> customerIds = jdbcTemplate.query("SELECT customer_id FROM wallet WHERE voucher_id = UUID_TO_BIN(?)",
                (resultSet, rowNum) -> {
                    UUID customerId = mapToUUID(resultSet.getBytes("customer_id"));
                    return customerId;
                },
                mapToBytes(voucherId)
        );

        return customerIds.stream()
                .map(customerId -> findByCustomerId(customerId))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(UUID customerId, UUID voucherId) {
        validateIsNotNull(customerId);
        validateIsNotNull(voucherId);

        int delete = jdbcTemplate.update("DELETE FROM wallet WHERE customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)", mapToBytes(customerId), mapToBytes(voucherId));

        if (delete != 1)
            throw new IllegalArgumentException(MessageFormat.format("Nothing was deleted. customerId = {0}, voucherId = {1}", customerId, voucherId));
    }

    @Transactional
    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM wallet");
    }

    @Override
    public boolean exist(UUID customerId, UUID voucherId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM wallet WHERE customer_id = UUID_TO_BIN(?) and voucher_id = UUID_TO_BIN(?)",
                Integer.class,
                customerId.toString().getBytes(),
                voucherId.toString().getBytes());

        return count == 1;
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<UUID> customerIds = jdbcTemplate.query("SELECT DISTINCT customer_id FROM wallet", (resultSet, rowNum) -> {
            UUID customerId = mapToUUID(resultSet.getBytes("customer_id"));
            return customerId;
        });

        return customerIds.stream()
                .map(customerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private byte[] mapToBytes(UUID id) {
        return id.toString().getBytes();
    }

    private UUID mapToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private void validateIsNotNull(UUID id) {
        Assert.notNull(id, "Input id should not be null!");
    }

    private void validateDuplicate(UUID customerId, UUID voucherId) {
        if (exist(customerId, voucherId)) {
            throw new IllegalArgumentException(MessageFormat.format("Voucher is existed in wallet. customerId = {0}  voucherId = {1}", customerId, voucherId));
        }
    }

}
