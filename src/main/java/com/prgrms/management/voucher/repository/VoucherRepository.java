package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(Voucher voucher);

    List<Voucher> findAll();
}
