package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    /**
     * db에 바우처 저장
     *
     * @param voucher
     * @return Voucher - db에 저장한 voucher
     */
    Voucher insert(Voucher voucher); //저장

    /**
     * db에 저장된 바우처 정보 수정
     *
     * @param voucher
     */
    void update(Voucher voucher); //수정

    /**
     * db에 저장된 바우처 삭제
     *
     * @param voucher
     */
    void delete(Voucher voucher); //삭제

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
    Optional<Voucher> findById(UUID voucherId); //조회
}
