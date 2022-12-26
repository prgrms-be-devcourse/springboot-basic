package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
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

    private static final int VOUCHER_ID_IDX = 0;
    private static final int VOUCHER_TYPE_IDX = 1;
    private static final int VOUCHER_DISCOUNT_AMOUNT_IDX = 2;
    private static final int VOUCHER_CUSTOMER_ID_IDX = 3;

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
                        String voucherIdCandidate = voucherInfo.split(",")[VOUCHER_ID_IDX];
                        return voucherIdCandidate.equals(voucherId.toString());
                    }).findFirst();

            if (findVoucher.isEmpty()) return Optional.empty();

            String[] infoArr = findVoucher.get().split(",");
            return Optional.of(
                    VoucherType.createVoucher(
                            UUID.fromString(infoArr[VOUCHER_ID_IDX]),
                            infoArr[VOUCHER_TYPE_IDX],
                            Integer.parseInt(infoArr[VOUCHER_DISCOUNT_AMOUNT_IDX]),
                            UUID.fromString(infoArr[VOUCHER_CUSTOMER_ID_IDX])
                    )
            );

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
        UUID uuid = UUID.fromString(voucherInfos[VOUCHER_ID_IDX]);
        String voucherType = voucherInfos[VOUCHER_TYPE_IDX];
        int discountAmount = Integer.parseInt(voucherInfos[VOUCHER_DISCOUNT_AMOUNT_IDX]);
        UUID customerId = UUID.fromString(voucherInfos[VOUCHER_CUSTOMER_ID_IDX]);

        return VoucherType.createVoucher(uuid, voucherType, discountAmount, customerId);
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
                    .filter(infoArr -> infoArr[VOUCHER_CUSTOMER_ID_IDX].equals(customerId.toString()))
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
                    .filter(voucherInfo -> !voucherInfo.split(",")[VOUCHER_CUSTOMER_ID_IDX].equals(customerId.toString()))
                    .forEach(voucherInfo -> voucherListTemp.append(voucherInfo).append(System.lineSeparator()));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(voucherListTemp.toString());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
