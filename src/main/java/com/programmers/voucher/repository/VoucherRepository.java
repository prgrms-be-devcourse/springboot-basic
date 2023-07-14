package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {

    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    Voucher findById(UUID voucherId);

    List<Voucher> findByType(String type);

    void deleteById(UUID voucherId);

    Page<Map<String, Object>> findAllByPage(Pageable pageable);
}
