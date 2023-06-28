package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.util.Loader;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Profile("file")
@Component // proxy -> new Proxy(FileVoucherRepository);
public class FileVoucherRepository implements VoucherRepository {
    // data load -> repository?
    // run -> file 1 read -> load -> map, List, Collection -> (running) -> sync -> -> close -> persist(save)
    @Value("${filePath.voucher}")
    private String filePath;
    private Map<UUID, Voucher> storage;

//    public FileVoucherRepository() {
//        this.storage = Loader.loadFileToMemoryVoucher(filePath);
//    }
    @PostConstruct
    public void init(){
        this.storage = Loader.loadFileToMemoryVoucher(filePath);
    }

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
    public List<Voucher> findAll() {
        return List.copyOf(storage.values());
    }

    @PreDestroy
    public void fileWrite() {
        Loader.saveMemoryVoucherToFile(storage, filePath);
    }
}
