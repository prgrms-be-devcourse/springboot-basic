package com.devcourse.voucher.domain.repository;

import com.devcourse.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import static com.devcourse.global.common.Constant.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@Profile("file")
class FileVoucherRepository implements VoucherRepository {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private final File vouchers = new File("src/main/resources/file/vouchers.txt");

    @Override
    public Voucher save(Voucher voucher) {
        try (FileWriter writer = new FileWriter(vouchers, true)) {
            String fileForm = voucher.toText(DELIMITER);
            writer.write(fileForm);

            return voucher;
        } catch (IOException e) {
            throw new RuntimeException("Save Fail");
        }
    }

    @Override
    public List<Voucher> findAll() {
        try(BufferedReader reader = new BufferedReader(new FileReader(vouchers, UTF_8))) {
            return reader.lines()
                    .map(this::toVoucher)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Read Fail");
        }
    }

    private Voucher toVoucher(String fileForm) {
        StringTokenizer tokenizer = new StringTokenizer(fileForm, DELIMITER);

        UUID id = UUID.fromString(tokenizer.nextToken());
        int discount = Integer.parseInt(tokenizer.nextToken());
        Voucher.Type type = Enum.valueOf(Voucher.Type.class, tokenizer.nextToken());
        LocalDateTime expiredAt = LocalDateTime.parse(tokenizer.nextToken(), TIME_FORMATTER);
        Voucher.Status status = Enum.valueOf(Voucher.Status.class, tokenizer.nextToken());

        return new Voucher(id, discount, expiredAt, type, status);
    }
}
