package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.exception.InvalidFilePathException;
import org.promgrammers.springbootbasic.exception.VoucherFileWriteException;
import org.promgrammers.springbootbasic.util.FileConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {


    private final Path filePath;
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository(@Value("${voucherStoragePath}") String voucherStoragePath) {
        this.filePath = Paths.get(voucherStoragePath);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            Files.write(filePath, Collections.singletonList(FileConverter.voucherToLine(voucher)), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            logger.error("Invalid Input => {}", e.getMessage());
            throw new InvalidFilePathException("IO 시스템 오류입니다.");
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Files.lines(filePath)
                    .map(FileConverter::parseVoucherFromLine)
                    .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                    .findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return Files.lines(filePath)
                    .map(FileConverter::parseVoucherFromLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteAll() {
        try {
            Files.write(filePath, Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new VoucherFileWriteException("Failed to delete all vouchers.");
        }
    }
}