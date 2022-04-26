package org.prgrms.vouchermanager.domain.voucher.service;

import org.prgrms.vouchermanager.domain.voucher.domain.Voucher;

import java.util.List;
import java.util.UUID;

/**
 * Voucher에 관한 서비스
 */
public interface VoucherService {
    /**
     * 바우처 생성
     *
     * @param type Voucher 타입.
     * @param amount 할인율
     * @return 생성된 Voucher의 UUID를 반환합니다.
     */
    UUID createVoucher(String type, Long amount);

    /**
     * 저장된 voucher 목록 조회
     */
    List<Voucher> findVouchers();

    /**
     * 아이디로 Voucher 조회
     *
     * @param voucherId
     * @return voucherId로 voucher찾아 반환합니다.
     */
    Voucher findVoucher(UUID voucherId);
}
