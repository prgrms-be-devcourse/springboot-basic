package org.prgrms.kdtspringdemo.voucher.repository;

import org.prgrms.kdtspringdemo.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    //CREATE
    Optional<Voucher> insert(Voucher voucher);

    //READ
    Optional<Voucher> findById(UUID voucherId);

    List<Voucher> findAllVaucher();

    //UPDATE
//    Voucher update(Voucher voucher);

    //DELETE
    void delete(UUID voucherId);

    void deleteAll();

}
