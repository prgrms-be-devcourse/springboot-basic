package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.vo.Type;
import org.prgrms.kdt.voucher.dto.VoucherDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherRepository {
    Voucher insert(VoucherDto voucher);

    List<Voucher> findAll();

    List<Voucher> findByEmail(Email email);

    UUID deleteById(UUID id);

    List<Voucher> findByType(Type type);

    List<Voucher> findByCreatedDate(LocalDateTime time);

    Voucher findById(UUID id);

}
