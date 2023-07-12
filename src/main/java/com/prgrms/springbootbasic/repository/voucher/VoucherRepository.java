package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    //create
    Voucher save(Voucher voucher);

    //read - id
    Optional<Voucher> findById(UUID voucherId);

    //read - create at
    Optional<Voucher> findByCreatedAt(LocalDateTime createAt);

    //read - all
    List<Voucher> findAll();


    //update
    Optional<Voucher> update(Voucher voucher);

    //delete - id

    Optional<Voucher> deleteById(UUID voucherId);

    //delete - all
    void deleteAll();
}
