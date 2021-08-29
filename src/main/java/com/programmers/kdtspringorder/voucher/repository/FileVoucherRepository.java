package com.programmers.kdtspringorder.voucher.repository;

import com.programmers.kdtspringorder.aop.TrackTime;
import com.programmers.kdtspringorder.voucher.domain.FixedAmountVoucher;
import com.programmers.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository {

    private final File file;
    private final BufferedWriter writer;
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository() throws IOException {
        file = new File("voucher.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        writer = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            List<String> strings = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return strings.stream()
                    .map(str -> str.split(" "))
                    .filter(arr -> arr[1].equals(voucherId.toString()))
                    .map(this::convertStringToVoucher)
                    .findFirst();
        } catch (IOException e) {
            logger.error("message : {0}", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        try {
            List<String> strings = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return strings.stream()
                    .map(str -> str.split(" "))
                    .map(this::convertStringToVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("message : {0}", e);
        }
        return new ArrayList<>();
    }

    private Voucher convertStringToVoucher(String[] voucherText) {
        if ("FixedAmountVoucher".equals(voucherText[0])) {
            return new FixedAmountVoucher(UUID.fromString(voucherText[1]), Long.parseLong(voucherText[2]));
        } else {
            return new PercentDiscountVoucher(UUID.fromString(voucherText[1]), Long.parseLong(voucherText[2]));
        }
    }

    @Override
    @TrackTime
    public Voucher save(Voucher voucher) {
        try {
            writer.write(voucher.getClass().getSimpleName() + " " + voucher.getVoucherId().toString() + " " + voucher.getValue());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
           logger.error("message : {0}", e);
        }
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {

    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findAllWithoutCustomerId() {
        return null;
    }

    @Override
    public void allocateVoucher(UUID voucherId, UUID customerId) {

    }

    @Override
    public void deallocateVoucher(UUID voucherId) {

    }
}
