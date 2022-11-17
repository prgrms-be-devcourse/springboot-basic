package org.prgrms.kdt.dao.repository.voucher;


import org.prgrms.kdt.dao.entity.voucher.Voucher;
import org.prgrms.kdt.dao.entity.voucher.VoucherFactory;
import org.prgrms.kdt.exception.io.WrongInputDataException;
import org.prgrms.kdt.exception.io.WrongOutputDataException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile({"dev", "test"})
public class FileVoucherRepository implements VoucherRepository {

    private static final List<Voucher> voucherRepository = new ArrayList<>();
    private final VoucherFactory voucherFactory;

    private final ClassPathResource resource;

    public FileVoucherRepository(VoucherFactory voucherFactory, @Value("${kdt.log-file}") String filePath) {
        this.voucherFactory = voucherFactory;
        this.resource = new ClassPathResource(filePath);
    }

    @PostConstruct
    void setUp() {
        try {
            for (String content : getAllLineFromFile()) {
                Voucher voucher = getVoucherStatus(parse(content));
                voucherRepository.add(voucher);
            }
        } catch (IOException e) {
            throw new WrongOutputDataException("바우처 파일을 읽어오는데 실패했습니다.", e);
        }
    }

    private List<String> getAllLineFromFile() throws IOException {
        Path pathResource = Paths.get(resource.getURI());
        return Files.readAllLines(pathResource);
    }

    private Voucher getVoucherStatus(String[] fields) {
        String voucherId = fields[0];
        String discountValue = fields[1];
        String voucherType = fields[2];

        return voucherFactory.createVoucher(UUID.fromString(voucherId), discountValue, voucherType);
    }

    private String[] parse(String line) {
        return line.split(",");
    }

    @PreDestroy
    void exit() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resource.getFile(), false))) {
            for (Voucher voucher : voucherRepository) {
                bw.write(voucher.getVoucherId().toString() + "," + voucher.getDiscountAmount() + "," + voucher.getVoucherType());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new WrongInputDataException("바우처를 삽입하기에 실패했습니다.", e);
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return voucherRepository.stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public void insert(Voucher voucher) {
        voucherRepository.add(voucher);
    }

    @Override
    public List<Voucher> getAllStoredVoucher() {
        return voucherRepository;
    }

    @Override
    public void clear() {
        voucherRepository.clear();
    }
}
