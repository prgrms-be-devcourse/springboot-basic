package org.prgrms.voucherapplication.repository.voucher;

import org.prgrms.voucherapplication.entity.Voucher;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 바우처 데이터를 관리하는 레포지터리
 */
public interface VoucherRepository {

    Optional<Voucher> findById(UUID voucherId) throws IOException;

    Voucher insert(Voucher voucher) throws IOException;

    List<Voucher> findAll() throws IOException;
}
