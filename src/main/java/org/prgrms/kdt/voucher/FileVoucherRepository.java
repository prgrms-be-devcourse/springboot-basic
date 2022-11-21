package org.prgrms.kdt.voucher;


import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherBuilder;
import org.prgrms.kdt.presentation.io.exception.WrongInputDataException;
import org.prgrms.kdt.presentation.io.exception.WrongOutputDataException;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final String BLANK = " ";
    private static final List<Voucher> voucherRepository = new ArrayList<>();
    private final VoucherBuilder voucherBuilder;

    private final ClassPathResource resource;

    public FileVoucherRepository(VoucherBuilder voucherBuilder, @Value("${repository.file}") String filePath) {
        this.voucherBuilder = voucherBuilder;
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

    @PreDestroy
    void exit() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resource.getFile(), false))) {
            for (Voucher voucher : voucherRepository) {
                String ownedCustomerId = voucher.getOwnedCustomerId().isEmpty() ? BLANK : voucher.getOwnedCustomerId().get().toString();
                bw.write(voucher.getVoucherId().toString() + "," + voucher.getDiscountAmount() + "," + voucher.getVoucherType() + "," + ownedCustomerId + "," + voucher.getCreatedAt());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new WrongInputDataException("바우처를 삽입하기에 실패했습니다.", e);
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
        String ownedCustomerId = fields[3];
        String createdAt = fields[4];

        return voucherBuilder.create()
                .setVoucherId(UUID.fromString(voucherId))
                .setDiscountAmount(discountValue)
                .setVoucherType(voucherType)
                .setOwnedCustomerId(ownedCustomerId)
                .setCreatedAt(Timestamp.valueOf(createdAt).toLocalDateTime())
                .build();
    }

    private String[] parse(String line) {
        return line.split(",");
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return voucherRepository.stream()
                .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                .findFirst();
    }

    @Override
    public Voucher update(Voucher voucher) {
        IntStream.range(0, voucherRepository.size())
                .filter(index -> voucherRepository.get(index).getVoucherId().equals(voucher.getVoucherId()))
                .findFirst()
                .ifPresent(index -> voucherRepository.set(index, voucher));

        return voucher;
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
