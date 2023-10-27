package team.marco.voucher_management_system.repository;

import java.util.List;
import team.marco.voucher_management_system.model.Voucher;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
