package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.config.CsvProperties;
import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.parser.voucher.CsvVoucherParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.springbootbasic.util.CharacterUnit.EMPTY;

@Repository
public class CsvVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(CsvVoucherRepository.class);
    private static long sequence;
    private final CsvVoucherParser csvParser = new CsvVoucherParser();
    private final CsvProperties csvProperties;

    @Autowired
    public CsvVoucherRepository(CsvProperties csvProperties) {
        this.csvProperties = csvProperties;
        initSequence();
    }

    private void initSequence() {
        List<Voucher> allVouchers = findAllVouchers();
        if (!allVouchers.isEmpty()) {
            sequence = allVouchers.get(allVouchers.size() - 1).getVoucherId();
        }
    }

    @Override
    public synchronized Voucher save(Voucher voucher) {
        try (Writer writer = new FileWriter(csvProperties.getVoucherCsvResource(), true)) {
            Voucher generatedVoucher =
                    VoucherFactory.of(++sequence, voucher.getDiscountValue(), voucher.getVoucherType());
            writer.write(csvParser.toCsvFrom(generatedVoucher));
            writer.flush();
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        Path csvPath = Paths.get(csvProperties.getVoucherCsvResource());
        List<String> voucherTexts = Collections.emptyList();
        try {
            voucherTexts = Files.readAllLines(csvPath);
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucherTexts.stream()
                .map(csvParser::toVoucherFrom)
                .collect(Collectors.toList());
    }

    @Override
    public List<Voucher> findAllVouchersByVoucherType(VoucherType voucherType) {
        Path csvPath = Paths.get(csvProperties.getVoucherCsvResource());
        List<String> voucherTexts = Collections.emptyList();
        try {
            voucherTexts = Files.readAllLines(csvPath);
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucherTexts.stream()
                .map(csvParser::toVoucherFrom)
                .filter(voucher -> voucher.getVoucherType() == voucherType)
                .collect(Collectors.toList());
    }

    @Override
    public Voucher findById(long voucherId) {
        return null;
    }

    @Override
    public void deleteAllVouchers() {
        try (Writer writer = new FileWriter(csvProperties.getVoucherCsvResource(), false)) {
            writer.write(EMPTY.unit());
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }
}
