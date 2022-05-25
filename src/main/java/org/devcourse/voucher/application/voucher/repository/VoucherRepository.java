package org.devcourse.voucher.application.voucher.repository;

import org.devcourse.voucher.application.voucher.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    Voucher update(Voucher voucher);

    Page<Voucher> findAll(Pageable pageable);

    void deleteAll();
}
