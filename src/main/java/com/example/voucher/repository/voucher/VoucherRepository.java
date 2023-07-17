package com.example.voucher.repository.voucher;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.example.voucher.domain.voucher.Voucher;

@Repository
public interface VoucherRepository {

    Voucher save(Voucher voucher);

    Voucher findById(UUID voucherID);

    void deleteById(UUID voucherID);

    List<Voucher> findAll();

    void deleteAll();

    Voucher update(Voucher voucher);
}
