package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherReaderRepository {
    /**
     * db에 저장된 전체 바우처 조회
     *
     * @return List<Voucher> - 등록된 전체 바우처
     */
    List<Voucher> findAll(); //전체 조회

    /**
     * voucherId를 이용해 바우처 조회
     *
     * @param voucherId
     * @return Optional<Voucher> - voucherId를 이용해 조회한 바우처
     */
    Optional<Voucher> findById(String voucherId); //조회
}
