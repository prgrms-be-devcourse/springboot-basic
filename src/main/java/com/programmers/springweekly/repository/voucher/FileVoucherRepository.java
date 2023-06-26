package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.*;
import com.programmers.springweekly.dto.ReadVoucherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
@Slf4j
public class FileVoucherRepository implements VoucherRepository {

    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    @Value("${file.voucher.path}")
    private String filePath;

    @Override
    public void saveVoucher(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {

            String voucherId = String.valueOf(voucher.getVoucherId());
            String voucherAmount = String.valueOf(voucher.getVoucherAmount());

            bufferedWriter.write(voucherId);
            bufferedWriter.write(",");
            bufferedWriter.write(voucherAmount);
            bufferedWriter.write(",");
            bufferedWriter.write(voucher.getVoucherType().getVoucherTypeString());
            bufferedWriter.newLine();

            bufferedWriter.flush();
        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }
    }

    @Override
    public Map<UUID, Voucher> getVoucherMap() {
        try(BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))){
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] readLine = line.split(",");

                ReadVoucherDto readVoucherDto = new ReadVoucherDto(readLine[0], readLine[1], readLine[2]);

                Voucher voucher =  VoucherFactory.createVoucher(readVoucherDto.getVoucherType(), readVoucherDto.getDiscountAmount());

                voucherMap.put(readVoucherDto.getVoucherId(), voucher);
            }
        } catch (Exception e) {
            log.error("error message: {}", e.getMessage());
        }

        return new ConcurrentHashMap<>(voucherMap);
    }
}
