package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;

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
     * 새 Voucher를 등록합니다
     * @param voucher
     */
    public void insert(Voucher voucher);

}
