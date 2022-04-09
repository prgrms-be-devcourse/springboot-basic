package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.configuration.FileDBProperties;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileVoucherRepository implements
    VoucherRepository, AutoCloseable {

  private static final String DELIMITER_REGEX = "\\|\\|";
  private static final String DELIMITER = "||";
  // 바우처를 로드했을 때 먼저 파일 스트림을 연다.
  private final BufferedWriter fileWriter;
  private final BufferedReader fileReader;
  private final Map<UUID, Voucher> memoryStorage = new LinkedHashMap<>();
  private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);


  public FileVoucherRepository(
      FileDBProperties fileDBProperties) throws IOException {
    String rootPath = System.getProperty("user.dir");
    var dbFile = Path.of(rootPath, fileDBProperties.getFilename()).toFile();

    fileWriter = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(dbFile, true)));
    fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(dbFile)));

    fileReader.lines().forEach(line -> {
      var fields = line.split(DELIMITER_REGEX);
      var voucherId = UUID.fromString(fields[0]);
      var voucherMapper = VoucherMapper.fromSimpleClassName(fields[1]);
      if (voucherMapper.isEmpty()) {
        return;
      }

      var discountDegree = Long.parseLong(fields[2].replace(",", ""));

      try {
        memoryStorage.put(voucherId,
            voucherMapper.get().getFactory().create(voucherId, discountDegree));
      } catch (VoucherDataOutOfRangeException e) {
        logger.error(MessageFormat.format("{0} : Not valid voucher", voucherId));
      }

    });
  }

  @Override
  public UUID insert(Voucher voucher) throws VoucherException {
    String template = "{1}{0}{2}{0}{3}";

    try {
      fileWriter.append(
          MessageFormat.format(template, DELIMITER,
              voucher.getVoucherId(),
              voucher.getClass().getSimpleName(),
              voucher.getDiscountDegree()));
      fileWriter.newLine();
      fileWriter.flush();
    } catch (IOException e) {
      throw new VoucherException("Repository insertion failed by IOException");
    }
    memoryStorage.put(voucher.getVoucherId(), voucher);
    return voucher.getVoucherId();
  }

  @Override
  public Optional<Voucher> getVoucher(UUID voucherId) {
    return Optional.ofNullable(memoryStorage.get(voucherId));
  }

  @Override
  public Map<UUID, Voucher> getAllVouchers() {
    return Collections.unmodifiableMap(memoryStorage);
  }

  @Override
  public void close() throws Exception {
    fileReader.close();
    fileWriter.close();
  }

}
