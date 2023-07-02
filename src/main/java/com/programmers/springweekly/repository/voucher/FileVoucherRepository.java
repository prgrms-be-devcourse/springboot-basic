package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.dto.ReadVoucherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"dev", "test"})
@Slf4j
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Value("${file.voucher.path}")
    private String filePath;

    @Override
    public void save(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {

            String voucherId = String.valueOf(voucher.getVoucherId());
            String voucherAmount = String.valueOf(voucher.getVoucherAmount());

            bufferedWriter.write(voucherId);
            bufferedWriter.write(",");
            bufferedWriter.write(voucherAmount);
            bufferedWriter.write(",");
            bufferedWriter.write(String.valueOf(voucher.getVoucherType()));
            bufferedWriter.newLine();

            bufferedWriter.flush();

        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }
    }

    @Override
    public Map<UUID, Voucher> getList() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] readLine = line.split(",");

                ReadVoucherDto readVoucherDto = new ReadVoucherDto(readLine[0], readLine[1], readLine[2]);

                Voucher voucher = VoucherFactory.createVoucherOfFile(readVoucherDto.getVoucherId(), readVoucherDto.getVoucherType(), readVoucherDto.getDiscountAmount());

                voucherMap.put(voucher.getVoucherId(), voucher);
            }

        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }

        return Collections.unmodifiableMap(voucherMap);
    }
}
