package team.marco.voucher_management_system.repository.voucher;

import team.marco.voucher_management_system.model.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();

    List<Voucher> findByOwner(UUID ownerId);

    Optional<Voucher> findById(UUID voucherId);

    Voucher update(Voucher voucher);

    void deleteById(UUID voucherId);


}
