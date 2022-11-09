package com.example.springbootbasic.repository;

import com.example.springbootbasic.config.AppConfiguration;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.parser.CsvVoucherParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class CsvVoucherRepository implements VoucherRepository {
    private static Long sequence = 0L;
    private final AppConfiguration appConfiguration;
    private final CsvVoucherParser csvParser = new CsvVoucherParser();


    @Autowired
    public CsvVoucherRepository(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public Long save(Long voucherId, Voucher voucher) {
        try (Writer writer = new FileWriter(appConfiguration.getVoucherCsvResource(), true)) {
            writer.write(csvParser.parseToCsvFrom(voucherId, voucher));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return voucherId;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        Path csvPath = Paths.get(appConfiguration.getVoucherCsvResource());
        try {
            List<String> voucherTexts = Files.readAllLines(csvPath);
            return voucherTexts.stream()
                    .map(csvParser::parseToVoucherFrom)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getSequence() {
        return sequence++;
    }
}
