package com.programmers.kwonjoosung.springbootbasicjoosung.repository.voucher;


import com.programmers.kwonjoosung.springbootbasicjoosung.config.FileVoucherProperties;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.Voucher;
import com.programmers.kwonjoosung.springbootbasicjoosung.utils.VoucherConverter;
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
import java.util.stream.Collectors;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final File voucherListTextFile;

    FileVoucherRepository(FileVoucherProperties fileVoucherProperties) {
        voucherListTextFile = new File(fileVoucherProperties.getFilePath() + fileVoucherProperties.getFileName());
    }

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try (Writer writer = new FileWriter(voucherListTextFile, true)) {
            writer.write(VoucherConverter.convertText(voucher));
            writer.flush();
            return Optional.of(voucher);
        } catch (IOException e) {
            logger.error("insert error message -> {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            List<String> voucherList = Files.readAllLines(voucherListTextFile.toPath());
            return voucherList.stream()
                    .map(VoucherConverter::textToVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("findAll error message -> {}", e.getMessage());
            return List.of();
        }
    }
}
