package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.UUID;

public interface VoucherStoreRepository {
    /**
     * db에 바우처 저장
     *
     * @param voucher
     * @return Voucher - db에 저장한 voucher
     */
    Voucher insert(Voucher voucher);

    /**
     * db에 저장된 바우처 정보 수정
     *
     * @param voucher
     */
    void update(Voucher voucher);

    /**
     * db에 저장된 바우처 삭제
     *
     * @param voucherId
     */
    void delete(UUID voucherId);
}
