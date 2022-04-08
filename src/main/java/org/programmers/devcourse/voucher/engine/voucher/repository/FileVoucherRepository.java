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
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.Voucher.VoucherMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class FileVoucherRepository implements
    VoucherRepository, AutoCloseable {

  // 바우처를 로드했을 때 먼저 파일 스트림을 연다.
  private final BufferedWriter dbWriter;
  private final BufferedReader dbReader;
  private final Map<UUID, Voucher> memoryStorage = new LinkedHashMap<>();
  private final String DELIMITER_REGEX = "\\|\\|";
  private final String DELIMITER = "||";


  public FileVoucherRepository() throws IOException {

    // 처음에 시도 했던 것

    String rootPath = System.getProperty("user.dir");
    var dbFile = Path.of(rootPath, "/voucher-db.txt").toFile();

    dbWriter = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(dbFile, true)));
    dbReader = new BufferedReader(new InputStreamReader(new FileInputStream(dbFile)));

    dbReader.lines().forEach(line -> {
      System.out.println(line);
      var record = line.split(DELIMITER_REGEX);

      System.out.println(record[0]);
      var voucherId = UUID.fromString(record[0]);
      var voucherType = VoucherMapper.fromClassName(record[1]);
      if (voucherType.isEmpty()) {
        return;
      }

      var discountDegree = Long.parseLong(record[2].replace(",", ""));

      memoryStorage.put(voucherId,
          voucherType.get().getFactory().create(voucherId, discountDegree));

    });
  }

  @Override
  public UUID insert(Voucher voucher) throws VoucherException {
    String template = "{1}{0}{2}{0}{3}";

    try {
      dbWriter.append(
          MessageFormat.format(template, DELIMITER,
              voucher.getVoucherId(),
              voucher.getClass().getSimpleName(),
              voucher.getDiscountDegree()));
      dbWriter.newLine();
      dbWriter.flush();
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
    dbReader.close();
    dbWriter.close();
  }
}
