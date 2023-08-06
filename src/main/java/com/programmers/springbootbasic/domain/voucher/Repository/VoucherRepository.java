package com.programmers.springbootbasic.domain.voucher.Repository;

import com.programmers.springbootbasic.common.domain.Page;
import com.programmers.springbootbasic.common.domain.Pageable;
import com.programmers.springbootbasic.domain.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    String NOT_SUPPORT = "현재 저장소에서는 해당 기능을 지원하지 않습니다.";

    UUID save(Voucher voucher);

    List<Voucher> findAll();

    long countTotalVoucher();

    default Page<Voucher> findAllWithPagination(Pageable pageable) {
        throw new UnsupportedOperationException(NOT_SUPPORT);
    }
}
