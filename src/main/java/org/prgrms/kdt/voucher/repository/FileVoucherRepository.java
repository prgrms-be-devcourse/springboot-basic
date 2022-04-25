package org.prgrms.kdt.voucher.repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.error.VoucherFileException;
import org.prgrms.kdt.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger log = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final ResourceLoader resourceLoader;

    @Value("${path.voucher}")
    private String pathFile;

    public FileVoucherRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Voucher save(Voucher voucher) {
        this.write(voucher);

        return voucher;
    }

    @Override
    public Collection<Voucher> findAll() {
        return read();
    }

    @Override
    public Voucher update(UUID voucherId, long value) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    private void write(Voucher voucher) {
        Resource resource = resourceLoader.getResource(pathFile);

        try (var file = new FileOutputStream(resource.getFile(), true);
            var out = new ObjectOutputStream(file)) {
            out.writeObject(voucher);
        } catch (IOException e) {
            log.error("파일에 읽어오는데 문제가 생겼습니다.");
            throw new VoucherFileException();
        }
    }

    private List<Voucher> read() {
        Resource resource = resourceLoader.getResource(pathFile);
        List<Voucher> vouchers = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(resource.getFile());) {
            while (file.available() > 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(file);
                Voucher voucher = (Voucher) objectInputStream.readObject();
                vouchers.add(voucher);
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("파일에 읽어오는데 문제가 생겼습니다.");
        }

        return vouchers;
    }

}
