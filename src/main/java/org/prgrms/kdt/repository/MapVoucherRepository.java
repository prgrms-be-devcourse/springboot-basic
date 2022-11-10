package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MapVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> memory = new HashMap<>();

    public Voucher save(Voucher voucher){
        return memory.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public Voucher getVoucherById(UUID voucherId) {
        return memory.get(voucherId);
    }

    public List<Voucher> getAll(){
        return new ArrayList<>(memory.values());
    };
}
