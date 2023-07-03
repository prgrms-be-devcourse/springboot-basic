package com.prgms.VoucherApp.domain.voucher.storage;

import com.prgms.VoucherApp.domain.voucher.FixedAmountVoucher;
import com.prgms.VoucherApp.domain.voucher.PercentDiscountVoucher;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class VoucherFileStorage implements VoucherStorage {

    private static final Logger log = LoggerFactory.getLogger(VoucherFileStorage.class);
    private final Map<UUID, Voucher> voucherLinkedMap = new ConcurrentHashMap<>();

    @Value("${voucher.file.path}")
    private String filePath;

    @PostConstruct
    public void initVoucherMap() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                VoucherDto voucherDto = createVoucherDto(line);
                Voucher voucher = createVoucher(voucherDto);
                voucherLinkedMap.put(voucher.getVoucherId(), voucher);
            }
        } catch (IOException e) {
            log.error("initVoucherMap() method Exception, message : {}", e.getMessage());
        }
    }

    @Override
    public void save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(voucher.getVoucherId().toString());
            bufferedWriter.write(",");
            bufferedWriter.write(voucher.getDiscountAmount().toString());
            bufferedWriter.write(",");
            bufferedWriter.write(voucher.getVoucherType().getVoucherTypeName());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            voucherLinkedMap.put(voucher.getVoucherId(), voucher);
        } catch (IOException e) {
            log.error("{} Failed to write the content to the file. errorMessage : {}", getClass().getEnclosingMethod().getName(), e.getMessage());
        }
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        Voucher voucher = voucherLinkedMap.get(voucherId);
        return Optional.ofNullable(voucher);
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
                return new FixedAmountVoucher(voucherDto.getVoucherId(), voucherDto.getDiscountAmount(), voucherDto.getVoucherType());
            case PERCENT_VOUCHER:
                return new PercentDiscountVoucher(voucherDto.getVoucherId(), voucherDto.getDiscountAmount(), voucherDto.getVoucherType());
        }

        log.warn("entered VoucherType {} is invalid", voucherDto.getVoucherType());
        throw new IllegalArgumentException(voucherDto.getVoucherType() + " is invalid voucher type");
    }

    @Override
    public void update(Voucher voucher) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }

    @Override
    public void deleteById(UUID id) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType type) {
        throw new RuntimeException("사용하지 않는 명령어 입니다.");
    }
}
