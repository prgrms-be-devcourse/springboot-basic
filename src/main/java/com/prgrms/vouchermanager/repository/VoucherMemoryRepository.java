package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    Map<UUID, Voucher> vouchers = new HashMap<>();

    public void create(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
    }

    public List<Voucher> list() {
        return vouchers
                .values()
                .stream()
                .toList();
    }
}
