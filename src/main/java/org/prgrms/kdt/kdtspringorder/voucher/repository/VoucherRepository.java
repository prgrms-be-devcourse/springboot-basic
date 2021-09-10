package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 바우처 데이터에 대한 CRUD를 담당합니다
 */
public interface VoucherRepository {

    /**
     * 모든 Voucher 목록을 조회합니다
     * @return
     */
    public List<Voucher> findAll();

    /**
     * id에 해당하는 Voucher 1개를 조회합니다
     * @param voucherId
     * @return
     */
    public Optional<Voucher> findById(UUID voucherId);

    /**
     * 고객이 보유한 바우처를 조회합니다.
     * @param customerId
     * @return
     */
    public List<Voucher> findByCustomerId(UUID customerId);

    public List<Voucher> findByVoucherType(VoucherType voucherType);

    /**
     * 새 Voucher를 등록합니다
     * @param voucher
     */
    public UUID insert(Voucher voucher);

    /**
     * 바우처 할인 금액/퍼센트를 변경합니다.
     * @param voucherId
     * @param amount
     * @return
     */
    public UUID updateDiscount(UUID voucherId, long amount);

    /**
     * 고객에게 바우처를 할당합니다.
     * @param voucherId
     * @param customerId
     * @return
     */
    public UUID updateCustomerId(UUID voucherId, UUID customerId);

    public int delete(UUID voucherId);

}
