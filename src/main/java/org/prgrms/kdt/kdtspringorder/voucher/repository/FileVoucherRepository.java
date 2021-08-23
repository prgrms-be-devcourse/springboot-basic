package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.common.io.FileIo;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Repository
@Profile({"local", "default"})
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID,Voucher> voucherMap = new HashMap<>();
    private final FileIo fileIo;

    public FileVoucherRepository(FileIo fileIo){
        this.fileIo = fileIo;
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(this.voucherMap.get(voucherId));
    }

    @Override
    public void insert(Voucher voucher) {
        this.voucherMap.put(voucher.getVoucherId(), voucher);
    }

    /**
     * 파일에서 데이터를 읽어와서 Map에 저장합니다.
     */
    @PostConstruct
    public void postConstruct() {

        List<Object> objectList = this.fileIo.readAllLines();
        objectList.forEach(o -> {
            Voucher v = (Voucher)o;
            voucherMap.put(v.getVoucherId(), v);
        });

    }

    /**
     * Map의 모든 내용을 File에 다시 씁니다.
     * @throws Exception
     */
    @PreDestroy
    public void destroy() throws Exception {

        ArrayList<Object> vouchers = new ArrayList<>(voucherMap.values());
        this.fileIo.write(vouchers);

    }

}
