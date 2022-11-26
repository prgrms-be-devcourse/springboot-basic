package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;


import com.programmers.kwonjoosung.springbootbasicjoosung.config.FileVoucherProperties;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final File voucherListTextFile;

    public FileVoucherRepository(FileVoucherProperties fileVoucherProperties) {
        voucherListTextFile = new File(fileVoucherProperties.getFilePath() + fileVoucherProperties.getFileName());
    }

    @Override
    public boolean insert(Voucher voucher) {
        try (Writer writer = new FileWriter(voucherListTextFile, true)) {
            writer.write(Converter.convertText(voucher));
            writer.flush();
            return true;
        } catch (IOException e) {
            logger.error("insert error message -> {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Voucher> findAll() {
        try {
            List<String> voucherList = Files.readAllLines(voucherListTextFile.toPath());
            return voucherList.stream()
                    .map(Converter::toVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("findAll error message -> {}", e.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean update(Voucher voucher) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(UUID voucherId) {
        throw new UnsupportedOperationException();
    }
}
