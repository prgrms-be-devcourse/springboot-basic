package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MapVoucherRepository implements VoucherRepository{

    private static final Map<UUID, Voucher> memory = new HashMap<>();

    public boolean save(Voucher voucher){
        memory.put(voucher.getVoucherId(), voucher);
        if(memory.containsKey(voucher.getVoucherId())) {
            return true;
        }
        return false;
    }

    @Override
        if (voucher.isPresent()){
            logger.info("[Repository] find voucher {}", voucher);
        }
        logger.info("[Repository] can't find the voucher id, {}", voucherId);
    }

    public List<Voucher> getAll(){
        return new ArrayList<>(memory.values());
    };
}
