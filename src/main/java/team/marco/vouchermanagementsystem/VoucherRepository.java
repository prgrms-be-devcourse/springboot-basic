package team.marco.vouchermanagementsystem;

import java.util.List;

public interface VoucherRepository {
    void save(Voucher voucher);

    List<Voucher> findAll();
}
