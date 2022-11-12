package com.programmers.commandline.domain.voucher.repository;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface VoucherRepository {
    Voucher save(Voucher voucher);
    List<Voucher> findAll();
}
