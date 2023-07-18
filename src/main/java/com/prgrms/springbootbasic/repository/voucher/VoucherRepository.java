package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    //create
    Voucher save(Voucher voucher);

    //read - id
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findByType(VoucherType type);

    //read - all
    List<Voucher> findAll();

    //read - create at
    List<Voucher> findByCreatedAt();

    //update
    void update(Voucher voucher);

    //delete - id
    int deleteById(UUID voucherId);

    //delete - all
    void deleteAll();

    boolean checkVoucherId(UUID voucherId);

}
