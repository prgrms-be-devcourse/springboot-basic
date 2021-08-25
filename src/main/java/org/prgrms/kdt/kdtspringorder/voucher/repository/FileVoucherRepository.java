package org.prgrms.kdt.kdtspringorder.voucher.repository;

import org.prgrms.kdt.kdtspringorder.common.io.FileIo;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdt.kdtspringorder.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Repository
@Profile({"local", "default"})
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final Map<UUID,Voucher> voucherMap = new HashMap<>();
    private final FileIo fileIo;

    public FileVoucherRepository(@Qualifier("file-object-io") FileIo<Object> fileIo){
        this.fileIo = fileIo;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Access findAll()");
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        logger.info("Access findById()");
        logger.info("[Param] voucherId = " + voucherId);
        return Optional.ofNullable(this.voucherMap.get(voucherId));
    }

    @Override
    public UUID insert(Voucher voucher) {
        logger.info("Access insert()");
        logger.info("[Param] voucher = " + voucher.toString());
        this.voucherMap.put(voucher.getVoucherId(), voucher);
        return voucher.getVoucherId();
    }

    /**
     * 파일에서 데이터를 읽어와서 Map에 저장합니다.
     */
    @PostConstruct
    public void postConstruct() {

        logger.info("Access postConstruct()");

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

        logger.info("Access destroy()");

        ArrayList<Object> vouchers = new ArrayList<>(voucherMap.values());
        this.fileIo.write(vouchers);

    }

}
