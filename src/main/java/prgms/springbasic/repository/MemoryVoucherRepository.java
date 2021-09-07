package prgms.springbasic.repository;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import prgms.springbasic.voucher.Voucher;

import java.util.*;

//@Repository
//@Qualifier("memoryVoucherRepository")
public class MemoryVoucherRepository implements VoucherRepository {

    private static Map<UUID, Voucher> repository = new HashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(repository.get(voucherId));
    }

    @Override
    public Voucher save(Voucher voucher) {
        repository.put(voucher.getVoucherId(), voucher);
        return repository.get(voucher.getVoucherId());
    }

    @Override
    public List<Voucher> getVoucherList() {
        List<Voucher> voucherList = new ArrayList<>();
        for (UUID uuid : repository.keySet()) {
            voucherList.add(repository.get(uuid));
        }
        return voucherList;
    }
}