package org.prgrms.kdt.voucher.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.io.FileIO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {

    private static final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();
    private final FileIO fileIO;

    public FileVoucherRepository(@Qualifier("file-object-io") FileIO<Object> fileIO){
        this.fileIO = fileIO;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        voucherStorage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherStorage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherStorage.values());
    }

    @PostConstruct
    public void postConstruct() {
        List<Object> list = fileIO.readAllLines();
        list.forEach(o -> {
            Voucher voucher = (Voucher)o;
            voucherStorage.put(voucher.getVoucherId(), voucher);
        });
    }

    @PreDestroy
    public void destroy() throws Exception{
        ArrayList<Object> vouchers = new ArrayList<>(voucherStorage.values());
        fileIO.write(vouchers);
    }
}
