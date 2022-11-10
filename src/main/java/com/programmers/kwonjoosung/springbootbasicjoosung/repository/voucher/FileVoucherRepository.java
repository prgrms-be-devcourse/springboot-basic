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
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("release")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final File voucherListTextFile;

    FileVoucherRepository(FileVoucherProperties fileVoucherProperties) {
        voucherListTextFile = new File(fileVoucherProperties.getFilePath() + fileVoucherProperties.getFileName());
    }

    @Override
    public Voucher insert(UUID voucherId, Voucher voucher) {
        try (Writer writer = new FileWriter(voucherListTextFile, true)) {
            writer.write(VoucherConverter.convertText(voucherId, voucher));
            writer.flush();
        } catch (IOException e) {
            logger.error("insert error message -> {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            List<String> voucherList = Files.readAllLines(voucherListTextFile.toPath());
            return voucherList.stream()
                    .map(VoucherConverter::textToVoucher)
                    .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                    .findAny();
        } catch (IOException e) {
            logger.error("findById error message -> {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
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
            logger.error("findAll error message -> {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
