package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.util.Converter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class BlackListFileDao {

    @Value("${customer.file.path}")
    private String filePath;

    public List<Customer> findBlacklist() {
        try {
            List<Customer> findBlacklists = Files.readAllLines(Paths.get(filePath))
                .stream()
                .map(Converter::convertToBlacklist)
                .toList();

            return findBlacklists;
        } catch (IOException e) {
            throw new RuntimeException("IO 문제로 바우처가 조회되지 않았습니다.", e);
        }
    }
}
