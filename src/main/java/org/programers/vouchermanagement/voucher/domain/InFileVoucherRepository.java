package org.programers.vouchermanagement.voucher.domain;

import org.programers.vouchermanagement.voucher.exception.NoSuchVoucherException;
import org.programers.vouchermanagement.util.Converter;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class InFileVoucherRepository implements VoucherRepository {

    private static final Path file = Paths.get("src/main/resources/voucher.txt");

    static {
        try {
            if (!Files.exists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            Files.writeString(file, String.format("%s%n", Converter.toString(voucher)),
                    StandardOpenOption.APPEND);
            return voucher;
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 저장되지 않았습니다.");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            List<String> lines = Files.readAllLines(file);
            for (String line : lines) {
                Voucher voucher = Converter.toVoucher(line);
                if (voucher.getId() == id) {
                    return Optional.of(voucher);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 조회되지 않았습니다.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return Files.readAllLines(file).stream()
                    .map(Converter::toVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new NoSuchVoucherException("IO 문제로 바우처가 조회되지 않았습니다.");
        }
    }
}
