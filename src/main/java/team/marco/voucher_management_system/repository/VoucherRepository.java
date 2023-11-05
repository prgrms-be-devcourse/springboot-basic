package team.marco.voucher_management_system.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.type_enum.VoucherType;

public interface VoucherRepository {
    void save(Voucher voucher);

    int deleteById(UUID id);

    List<Voucher> findAll();

    Optional<Voucher> findById(UUID id);

    List<Voucher> findByType(VoucherType voucherType);

    List<Voucher> findByCreateAt(LocalDateTime from, LocalDateTime to);
}
