package com.prgms.VoucherApp.storage;

import com.prgms.VoucherApp.domain.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Repository
@Profile("dev")
public class VoucherFileStorage implements VoucherStorage {


    private static final Logger log = LoggerFactory.getLogger(VoucherFileStorage.class);
    private final Map<UUID, Voucher> voucherLinkedMap = new LinkedHashMap<>();

    @Value("${file.path}")
    private String filePath;

    @PostConstruct
    public void initVoucherMap() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                VoucherDto voucherDto = createVoucherDto(line);
                Voucher voucher = createVoucher(voucherDto);
                voucherLinkedMap.put(voucher.getUUID(), voucher);
            }
        } catch (IOException e) {
            log.error("findAll() method Exception, message : {}", e.getMessage());
        }
    }

    @Override
    public void save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            VoucherDto voucherDto = voucher.convertVoucherDto();
            String voucherId = String.valueOf(voucherDto.getVoucherId());
            String discountAmount = String.valueOf(voucherDto.getDiscountAmount());
            String voucherType = String.valueOf(voucherDto.getVoucherType().getVoucherPolicy());
            bufferedWriter.write(voucherId);
            bufferedWriter.write(",");
            bufferedWriter.write(discountAmount);
            bufferedWriter.write(",");
            bufferedWriter.write(voucherType);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error("{} Failed to write the content to the file. errorMessage : {}", getClass().getEnclosingMethod().getName(), e.getMessage());
        }

        voucherLinkedMap.put(voucher.getUUID(), voucher);
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        Voucher voucher = voucherLinkedMap.get(voucherId);
        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherLinkedMap.values()
                .stream()
                .toList();
    }

    private VoucherDto createVoucherDto(String line) {
        String[] csvLine = line.split(",");
        String voucherId = csvLine[0];
        String discountAmount = csvLine[1];
        String voucherType = csvLine[2];
        return new VoucherDto(voucherId, discountAmount, voucherType);
    }

    private Voucher createVoucher(VoucherDto voucherDto) {
        switch (voucherDto.getVoucherType()) {
            case FIXED_VOUCHER:
                return new FixedAmountVoucher(voucherDto.getVoucherId(), voucherDto.getDiscountAmount());
            case PERCENT_VOUCHER:
                return new PercentDiscountVoucher(voucherDto.getVoucherId(), voucherDto.getDiscountAmount());
        }

        log.warn("entered VoucherType {} is invalid", voucherDto.getVoucherType());
        throw new IllegalArgumentException(voucherDto.getVoucherType() + "is invalid voucher type");
    }
}
