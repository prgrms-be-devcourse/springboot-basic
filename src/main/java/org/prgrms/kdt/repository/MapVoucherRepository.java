package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MapVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> memory;
    private static final Logger logger = LoggerFactory.getLogger(MapVoucherRepository.class);

    public MapVoucherRepository(){
        this.memory = new HashMap<>();
    }

    public boolean save(Voucher voucher){
        memory.put(voucher.getVoucherId(), voucher);
        if(memory.containsKey(voucher.getVoucherId())) {
            logger.info("[Repository] save {}", voucher);
            return true;
        }
        logger.info("[Repository] can't save the voucher id, {}", voucher.getVoucherId());
        return false;
    }

    public Optional<Voucher> getVoucherById(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(memory.get(voucherId));
        if (voucher.isPresent()){
            logger.info("[Repository] find voucher {}", voucher);
        }
        logger.info("[Repository] can't find the voucher id, {}", voucherId);
        return voucher;
    }

    public List<Voucher> getAll(){
        logger.info("[Repository] get all vouchers");
        return new ArrayList<>(memory.values());
    };
}
