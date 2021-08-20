package org.prgms.order.voucher.repository;

import org.prgms.order.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("Memory")
public class MemoryVoucherRepository implements VoucherRepository{
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder( "         TYPE              AMOUNT\n");
        Set<UUID> keySet = storage.keySet();

        for(UUID uuid : keySet){
            returnString.append(storage.get(uuid).toString()).append("\n");

        }

        return returnString.toString();
    }
}