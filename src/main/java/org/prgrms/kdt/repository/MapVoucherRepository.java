package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("map")
@Repository
public class MapVoucherRepository implements VoucherRepository {

    private final Map<Long, Voucher> memory;
    private static final Logger logger = LoggerFactory.getLogger(MapVoucherRepository.class);

    public MapVoucherRepository(){
        this.memory = new HashMap<>();
    }

    public Optional<Voucher> saveVoucher(Voucher voucher){
        long voucherId = voucher.getId();
        if(voucher.getId() == 0){
            voucherId = memory.size() + 1;
            voucher.setId(voucherId);
        }
        memory.put(voucherId, voucher);
        if(memory.containsKey(voucher.getId())) {
            logger.info("[Repository] save {}", voucher);
            return Optional.of(voucher);
        }
        logger.info("[Repository] can't save the voucher id, {}", voucher.getId());
        return Optional.empty();
    }

    public Optional<Voucher> getVoucherById(long voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(memory.get(voucherId));
        if (voucher.isPresent()){
            logger.info("[Repository] find voucher {}", voucher);
        }
        logger.info("[Repository] can't find the voucher id, {}", voucherId);
        return voucher;
    }

    public List<Voucher> getAllVouchers(){
        logger.info("[Repository] get all vouchers");
        return new ArrayList<>(memory.values());
    };
}
