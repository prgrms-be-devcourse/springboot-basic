package org.prgrms.vouchermanager.domain.voucher.service;

import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;

import java.util.UUID;

/**
 * Voucher에 관한 서비스
 */
public interface VoucherService {
    /**
     * 타입과 할인율을 입력받아 Voucher를 생성하고 repository에 저장합니다.
     * @param type Voucher 타입.
     * @param amount 할인율
     * @return 생성된 Voucher의 UUID를 반환합니다.
     */
    UUID createVoucher(String type, Long amount);

    /**
     * @return 저장된 voucher 목록을 String으로 반환합니다.
     */
    String allVouchersToString();

    /**
     * @param voucherId
     * @return voucherId로 voucher찾아 반환합니다.
     */
    Voucher findVoucher(UUID voucherId);
}
