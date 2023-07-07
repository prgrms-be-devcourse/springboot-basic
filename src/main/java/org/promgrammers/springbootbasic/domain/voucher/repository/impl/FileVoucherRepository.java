package org.promgrammers.springbootbasic.domain.voucher.repository.impl;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.repository.VoucherRepository;
import org.promgrammers.springbootbasic.exception.repository.FileWriteException;
import org.promgrammers.springbootbasic.exception.repository.InvalidFilePathException;
import org.promgrammers.springbootbasic.util.FileConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
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
    public void assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        try {
            List<String> lines = Files.lines(filePath)
                    .map(line -> {
                        Voucher voucher = FileConverter.parseVoucherFromLine(line);
                        if (voucher.getVoucherId().equals(voucherId)) {
                            voucher.assignCustomerId(customerId);
                        }
                        return FileConverter.voucherToLine(voucher);
                    })
                    .collect(Collectors.toList());

            Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            logger.error("Voucher 할당 실패", e.getMessage());
            throw new FileWriteException("Voucher 할당에 실패했습니다.");
        }
    }

    @Override
    public void removeVoucherFromCustomer(UUID customerId, UUID voucherId) {
        try {
            List<String> lines = Files.lines(filePath)
                    .map(line -> {
                        Voucher voucher = FileConverter.parseVoucherFromLine(line);
                        if (voucher.getVoucherId().equals(voucherId) && voucher.getCustomerId().equals(customerId)) {
                            voucher.assignCustomerId(null);
                        }
                        return FileConverter.voucherToLine(voucher);
                    })
                    .collect(Collectors.toList());

            Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            logger.error("Voucher 제거 실패", e.getMessage());
            throw new FileWriteException("Voucher 제거에 실패했습니다.");
        }
    }

    @Override
    public List<Voucher> findAllByCustomerId(UUID customerId) {
        try {
            return Files.lines(filePath)
                    .map(FileConverter::parseVoucherFromLine)
                    .filter(voucher -> Objects.equals(voucher.getCustomerId(), customerId))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Voucher 목록 조회 실패", e.getMessage());
            throw new FileWriteException("Voucher 목록 조회에 실패했습니다.");
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        try {
            List<String> lines = Files.lines(filePath)
                    .map(line -> FileConverter.voucherToLine(FileConverter.parseVoucherFromLine(line)))
                    .collect(Collectors.toList());

            String updatedLine = FileConverter.voucherToLine(voucher);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                UUID voucherId = UUID.fromString(parts[0]);

                if (voucherId.equals(voucher.getVoucherId())) {
                    lines.set(i, updatedLine);
                    break;
                }
            }

            Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
            return voucher;
        } catch (IOException e) {
            logger.error("Voucher 업데이트 실패", e.getMessage());
            throw new FileWriteException("Voucher 업데이트에 실패했습니다.");
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.write(filePath, Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new FileWriteException("Failed to delete all vouchers.");
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        try {
            List<String> lines = Files.lines(filePath)
                    .filter(line -> {
                        UUID id = UUID.fromString(line.split(",")[0]);
                        return !id.equals(voucherId);
                    })
                    .collect(Collectors.toList());

            Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            logger.error("Voucher 삭제 실패", e.getMessage());
            throw new FileWriteException("Voucher 삭제에 실패했습니다.");
        }
    }
}