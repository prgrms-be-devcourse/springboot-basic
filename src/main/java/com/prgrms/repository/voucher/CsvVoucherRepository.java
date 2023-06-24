package com.prgrms.repository.voucher;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CsvVoucherRepository implements VoucherRepository {
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }
    @Override
    public Voucher insert(Voucher voucher) {
        return null;
    }

    @Override
    public List<VoucherResponse> getAllVoucherList() {
        return null;
    }
}

