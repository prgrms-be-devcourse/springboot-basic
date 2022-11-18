package org.prgrms.springbootbasic.repository;

import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;

@Repository
@Profile(value = "dev")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Autowired
    private final ResourceLoader resourceLoader;

    public FileVoucherRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void insert(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent()) {
            logger.info("duplicated voucher");
            return;
        }
        StringBuffer newVoucherString = new StringBuffer().append("\n")
                .append(VoucherType.ClassToVoucherType(voucher)).append(",")
                .append(voucher.getVoucherId()).append(",")
                .append(voucher.getQuantity());
        try {
            Files.writeString(getPath(), newVoucherString, APPEND);
        } catch (IOException e) {
            logger.error("can't open file");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<String> lines = getLines();
        if (lines.size() == 0) {
            return Optional.empty();
        }
        return lines.stream()
                .map(line -> line.split(","))
                .filter(Info -> UUID.fromString(Info[1]).equals(voucherId))
                .findFirst()
                .map(this::voucherInfo2Voucher);
    }

    @Override
    public List<Voucher> findAll() {
        List<String> lines = getLines();
        if (lines.size() == 0) {
            return Collections.emptyList();
        }

        return lines.stream()
                .map(line -> line.split(","))
                .map(this::voucherInfo2Voucher)
                .collect(Collectors.toList());
    }

    private Path getPath() throws IOException {
        return resourceLoader.getResource("fileVoucherRepository.csv").getFile().toPath();
    }

    private List<String> getLines() {
        try {
            return Files.readAllLines(getPath());
        } catch (IOException e) {
            logger.error("can't read file", e);
            throw new RuntimeException();
        }
    }

    private Voucher voucherInfo2Voucher(String[] voucherInfoArr) {
        Voucher voucher;
        try {
            Class<? extends Voucher> voucherClass = VoucherType.valueOf(voucherInfoArr[0]).getVoucherClass();
            voucher = voucherClass.getConstructor(UUID.class, long.class)
                    .newInstance(UUID.fromString(voucherInfoArr[1]), Long.parseLong(voucherInfoArr[2]));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            logger.error("voucherInfo does not converted to Voucher");
            throw new IllegalStateException();
        }
        return voucher;
    }
}
