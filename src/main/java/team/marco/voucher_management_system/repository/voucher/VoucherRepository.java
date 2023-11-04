package team.marco.voucher_management_system.repository.voucher;

import team.marco.voucher_management_system.domain.voucher.Voucher;
import team.marco.voucher_management_system.domain.voucher.VoucherType;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository {
    Voucher save(Voucher voucher);

    List<Voucher> findAll();
    
    List<Voucher> findAllByVoucherType(VoucherType type);

    Optional<Voucher> findById(Long voucherId);

    void deleteById(Long voucherId);

    Optional<Long> findLatestVoucherId();
}
