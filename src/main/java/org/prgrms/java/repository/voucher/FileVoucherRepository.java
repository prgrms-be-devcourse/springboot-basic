package org.prgrms.java.repository.voucher;

import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;
import org.prgrms.java.service.mapper.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private final String DATA_PATH;
    private final String DATA_NAME;
    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository(@Value("${prgrms.data.path}") String DATA_PATH, @Value("${prgrms.data.name.voucher}") String DATA_NAME) {
        logger.debug("저장 파일 생성 중...");
        try {
            new File(DATA_PATH).mkdirs();
            new File(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.DATA_PATH = DATA_PATH;
        this.DATA_NAME = DATA_NAME;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            Optional<String> str = reader.lines()
                    .filter(line -> line.contains(voucherId.toString()))
                    .findAny();
            if (str.isPresent()) {
                return Optional.of(VoucherMapper.mapToVoucher(str.get()));
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Voucher> findByCustomer(UUID customerId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            return reader.lines()
                    .filter(line -> line.contains(customerId.toString()))
                    .map(VoucherMapper::mapToVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Voucher> findExpiredVouchers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            return reader.lines()
                    .filter(line -> LocalDateTime.parse(line.split(",")[5].trim()).isBefore(LocalDateTime.now()))
                    .map(VoucherMapper::mapToVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Voucher> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            return reader.lines()
                    .map(VoucherMapper::mapToVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new VoucherBadRequestException("이미 존재하는 아이디입니다.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME), true))) {
            writer.write(voucher.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            for (String line : lines) {
                if (line.contains(voucher.getVoucherId().toString())) {
                    writer.write(voucher.toString());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            for (String line : lines) {
                if (!line.contains(voucherId.toString())) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String fileName = MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            new FileWriter(fileName).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
