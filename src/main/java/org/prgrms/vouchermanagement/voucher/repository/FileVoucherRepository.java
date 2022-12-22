package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.domain.dto.VoucherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Profile("file")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final String path;

    public FileVoucherRepository(@Value("${repository.file.voucher.path}") String path) {
        this.path = path;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        try {
            List<String> vouchers = Files.readAllLines(Paths.get(path));
            Optional<String> findVoucher = vouchers.stream()
                    .filter(voucherInfo -> {
                        String voucherIdCandidate = voucherInfo.split(",")[0];
                        return voucherIdCandidate.equals(voucherId.toString());
                    }).findFirst();

            if (findVoucher.isEmpty()) return Optional.empty();

            String[] infoArr = findVoucher.get().split(",");
            return Optional.of(
                    VoucherType.createVoucher(
                            VoucherVO.of(UUID.fromString(infoArr[0]), infoArr[1], Integer.parseInt(infoArr[2]), UUID.fromString(infoArr[3]))));

        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(parseCsvFormat(voucher.getVoucherId().toString(),
                    voucher.getVoucherType().name(),
                    String.valueOf(voucher.getDiscountAmount()),
                    voucher.getCustomerId().toString()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try {
            List<String> voucherInfos = Files.readAllLines(Paths.get(path));
            voucherInfos.forEach(info -> vouchers.add(createVoucher(info.split(","))));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return vouchers;
    }

    private String parseCsvFormat(String... input) {
        return String.join(",", input) + System.lineSeparator();
    }

    private Voucher createVoucher(String[] voucherInfos) {
        UUID uuid = UUID.fromString(voucherInfos[0]);
        String voucherType = voucherInfos[1];
        int discountAmount = Integer.parseInt(voucherInfos[2]);
        UUID customerId = UUID.fromString(voucherInfos[3]);

        return VoucherType.createVoucher(VoucherVO.of(uuid, voucherType, discountAmount, customerId));
    }

    @Override
    public void clear() {
        try {
            Files.writeString(Paths.get(path), "");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        try {
            List<String> voucherInfos = Files.readAllLines(Paths.get(path));
            return voucherInfos.stream()
                    .map(infoStr -> infoStr.split(","))
                    .filter(infoArr -> infoArr[3].equals(customerId.toString()))
                    .map(this::createVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return List.of();
    }

    @Override
    public void deleteVoucherByCustomerId(UUID customerId) {
        StringBuilder voucherListTemp = new StringBuilder();
        try {
            List<String> voucherInfos = Files.readAllLines(Paths.get(path));
            voucherInfos.stream()
                    .filter(voucherInfo -> !voucherInfo.split(",")[3].equals(customerId.toString()))
                    .forEach(voucherInfo -> voucherListTemp.append(voucherInfo).append("\n"));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(voucherListTemp.toString());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
