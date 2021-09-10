package org.prgrms.kdt.kdtspringorder.voucher.service;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {

    /**
     * 모든 Voucher 목록을 조회합니다.
     * @return 조회한 Voucher 목록을 반환합니다.
     */
    public List<Voucher> getVouchers();

    /**
     * voucherId에 해당하는 바우처 1개를 조회합니다.
     *
     * @param voucherId
     * @return 조회한 바우처를 반환합니다.
     */
    public Voucher getVoucher(UUID voucherId);

    public List<Voucher> getVouchersByCustomer(UUID customerId);

    public List<Voucher> getVouchersByVoucherType(VoucherType voucherType);

    /**
     * 새 Voucher를 생성하여 등록합니다.
     * @param type 생성할 Voucher 유형
     * @param discount 할인 받을 정도 ( 금액 or % )
     */
    public UUID saveVoucher(VoucherType type, long discount);

    public void updateVoucherDiscountAmount(UUID voucherId, long amount);

    public void allocateVoucherToCustomer(UUID voucherId, UUID customerId);

    public void deleteVoucher(UUID voucherId);



}
