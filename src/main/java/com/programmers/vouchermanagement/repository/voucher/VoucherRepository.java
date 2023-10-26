package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    List<Voucher> findByName(String name);

    Voucher save(Voucher voucher);

    void delete(UUID id);


}
