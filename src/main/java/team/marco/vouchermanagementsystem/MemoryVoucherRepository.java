package team.marco.vouchermanagementsystem;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
    @Override
    public void save(Voucher voucher) {

    }

    @Override
    public List<Voucher> findAll() {
        return null;
    }
}
