package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {
    @Override
    public Voucher save(Voucher voucher) {
        return null;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }

    @Override
    public void findCustomerIdByVoucherType(VoucherType voucherType) {

    }
}
