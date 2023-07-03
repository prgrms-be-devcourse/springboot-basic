package org.prgms.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.prgms.voucher.dto.BlackCustomerResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class FileBlackCustomerRepository implements BlackCustomerRepository {

    @Value("${black-list-file-path}")
    private String filePath;

    @Override
    public List<BlackCustomerResponseDto> findAll() {

        List<BlackCustomerResponseDto> customers = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                customers.add(parseCSV(line));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return customers;
    }

    private BlackCustomerResponseDto parseCSV(String line) {
        String[] data = line.split(",");
        long id = Integer.parseInt(data[0]);
        String name = data[1];

        return new BlackCustomerResponseDto(id, name);
    }
}
