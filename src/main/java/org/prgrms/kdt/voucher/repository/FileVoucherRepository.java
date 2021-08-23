package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.util.VoucherFileManager;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("file")
//@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private final VoucherFileManager voucherFileManager;
    private final ConcurrentHashMap<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
    private static final String FILE_PATH = "./data/voucher.csv";

    public FileVoucherRepository(VoucherFileManager voucherFileManager) {
        this.voucherFileManager = voucherFileManager;
    }

    @Override
    public Voucher save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
        voucherFileManager.memoryToFile(FILE_PATH, voucherMap);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(voucherMap.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    // 해당 빈이 생기기전에 파일을 읽어서 메모리에 로드한다.
    @PostConstruct
    public void postConstruct() {
        voucherMap.putAll(voucherFileManager.fileToMemory(FILE_PATH));
    }

    // 빈이 소멸될때 메모리값을 파일에 쓴다.
    @PreDestroy
    public void preDestroy() {
        voucherFileManager.memoryToFile(FILE_PATH, voucherMap);
    }
}