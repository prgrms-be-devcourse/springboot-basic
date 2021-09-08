package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.util.FileConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Profile("prod")
@Repository
public class FileVoucherRepositoy implements VoucherRepository, FileConnector<Voucher> {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepositoy.class);

    private static Map<UUID, Voucher> storage;

    private final String PATH = "src/main/resources/voucher.csv";

    // Postconsruct 할 때 열어서 메모리로 쌓고 preDestroy가 일어날 때 닫고
    // File과 저장소를 연결하는 provider 클래스를 만들어보자
    // findbyId 구현

    @PostConstruct
    private void postConstruct() {
        storage = fileConnect(PATH);
    }

    @PreDestroy
    private void preDestory() {
        fileInsert(PATH, storage);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    // 이 아래에서 부턴 DB에서 쓰는 영역

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Voucher> findByVoucherWalletId(UUID customerId) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return null;
    }
}
