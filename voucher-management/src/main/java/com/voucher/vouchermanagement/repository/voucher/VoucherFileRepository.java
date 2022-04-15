package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.repository.io.FileInput;
import com.voucher.vouchermanagement.repository.io.FileOutput;
import com.voucher.vouchermanagement.repository.utils.CsvDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Profile("prod")
public class VoucherFileRepository implements VoucherRepository {

    private final String voucherDbName = "voucher_db.csv";
    private final Resource voucherDb = new ClassPathResource("db/" + voucherDbName);
    private final FileOutput output;
    private final FileInput input;
    private final CsvDeserializer<Voucher> voucherDeserializer;
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

    public VoucherFileRepository(FileOutput output, FileInput input,
                                 @Qualifier("voucherDeserializer") CsvDeserializer<Voucher> voucherDeserializer) {
        this.output = output;
        this.input = input;
        this.voucherDeserializer = voucherDeserializer;
    }

    @Override
    public void insert(Voucher voucher) {
        try {
            String voucherCsvData = String.join(",", voucher.getClass().getSimpleName(),
                    voucher.getVoucherId().toString(),
                    voucher.getValue().toString(),
                    voucher.getCreatedAt().toString());

            output.writeln(voucherDb.getFile(), voucherCsvData);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            List<String> foundDataStrings = input.readAllLine(voucherDb.getFile());

            return foundDataStrings
                    .stream()
                    .map(voucherDeserializer::deserialize)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return Collections.emptyList();
    }

}
