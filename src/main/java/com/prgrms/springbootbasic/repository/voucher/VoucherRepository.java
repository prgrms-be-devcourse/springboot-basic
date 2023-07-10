package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

    //create
    Voucher create(Voucher voucher);

    //read
    Voucher findById(UUID voucherId);

    Voucher findByCreatedAt(LocalDateTime createAt);

    List<Voucher> findAll();

    //update
    void update(Voucher voucher);

    //delete
    void deleteById(UUID voucherId);

    void deleteAll();
}
