package org.prgrms.deukyun.voucherapp.domain.voucher.repository;

import org.prgrms.deukyun.voucherapp.domain.common.repository.MemoryRepository;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;
import org.springframework.stereotype.Repository;

/**
 * 메모리 바우처 리포지토리
 */
@Repository
public class MemoryVoucherRepository extends MemoryRepository<Voucher> implements VoucherRepository {
}
