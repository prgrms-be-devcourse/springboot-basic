package org.prgms.voucher.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.prgms.voucher.dto.VoucherResponseDto;
import org.prgms.voucher.voucher.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${voucher-list-file-path}")
    private String filePath;

    @Override
    public List<VoucherResponseDto> findAll() {

        List<VoucherResponseDto> vouchers = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                vouchers.add(objectMapper.readValue(line, VoucherResponseDto.class));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return vouchers;
    }

    @Override
    public Voucher save(Voucher voucher) {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
            String voucherJson = objectMapper.writeValueAsString(voucher);
            bufferedWriter.write(voucherJson);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return voucher;
    }

    @Override
    public Optional<VoucherResponseDto> findById(UUID id) {

        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                VoucherResponseDto voucherResponseDto = objectMapper.readValue(line, VoucherResponseDto.class);
                if (voucherResponseDto.getId().equals(id)) {
                    return Optional.of(voucherResponseDto);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.empty();
    }
}
