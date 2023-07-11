package com.prgms.VoucherApp.domain.voucher.model;

import com.prgms.VoucherApp.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class VoucherFileDao implements VoucherDao {

    private static final Logger log = LoggerFactory.getLogger(VoucherFileDao.class);

    @Value("${voucher.file.path}")
    private String filePath;

    @Override
    public void save(Voucher voucher) {
        try {
            Files.writeString(
                Paths.get(filePath),
                String.format("%s%n", Converter.toString(voucher)),
                StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("IO 문제로 바우처가 저장되지 않았습니다.", e);
        }
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        try {
            List<String> voucherFileContents = Files.readAllLines(Paths.get(filePath));
            voucherFileContents.forEach((voucher) -> {
                System.out.println("읽어 들인 voucher : " + voucher);
            });
            for (String record : voucherFileContents) {
                Voucher voucher = Converter.convertToVoucher(record);
                if (voucher.getVoucherId().equals(voucherId)) {
                    return Optional.of(voucher);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException("IO 문제로 바우처가 조회되지 않았습니다.", e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            List<Voucher> findVouchers = Files.readAllLines(Paths.get(filePath))
                .stream()
                .map(Converter::convertToVoucher)
                .collect(Collectors.toList());
            return findVouchers;
        } catch (IOException e) {
            throw new RuntimeException("IO 문제로 바우처가 조회되지 않았습니다.", e);
        }
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType type) {
        try {
            List<Voucher> findVouchers = Files.readAllLines(Paths.get(filePath))
                .stream()
                .map(Converter::convertToVoucher)
                .toList();

            return findVouchers;
        } catch (IOException e) {
            throw new RuntimeException("IO 문제로 바우처가 조회되지 않았습니다.", e);
        }
    }

    @Override
    public void update(Voucher voucher) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }

    @Override
    public void deleteById(UUID id) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }
}
