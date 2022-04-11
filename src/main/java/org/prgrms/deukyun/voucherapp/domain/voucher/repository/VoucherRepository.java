package org.prgrms.deukyun.voucherapp.domain.voucher.repository;

import org.prgrms.deukyun.voucherapp.domain.common.repository.Repository;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.Voucher;

import java.util.UUID;

/**
 * 바우처 리포지토리
 */
public interface VoucherRepository extends Repository<Voucher, UUID> {

}
