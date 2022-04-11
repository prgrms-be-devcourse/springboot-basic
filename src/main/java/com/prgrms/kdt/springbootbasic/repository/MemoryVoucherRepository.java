package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.outputPackage.ConsoleOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository{
    private static Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

    @Override
    public Voucher findById(UUID voucherId) {
        return voucherStorage.get(voucherId);
    }

    @Override
    public Voucher saveVoucher(Voucher voucher){
        voucherStorage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getAllVouchers(){
        return voucherStorage.values().stream().collect(Collectors.toList());
    }
}
