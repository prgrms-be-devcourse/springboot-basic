package org.prgrms.kdtspringorder.voucher.repository.implementation;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.voucher.enums.VoucherPolicy;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FileVoucherRepository implements VoucherRepository {

  public static final int COL_UUID = 0;
  public static final int COL_VOUCHER_TYPE = 1;
  private String filePath = "src/main/resources/tempRepo.csv";

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    List<Voucher> vouchers = this.getVouchers();
    return vouchers.stream().filter(voucher -> voucher.getId().equals(voucherId)).findFirst();
  }

  @Override
  public List<Voucher> getVouchers() {
    CSVReader csvReader = getCSVReader();
    List<String[]> rows;

    try {
      rows = csvReader.readAll();
    } catch (IOException | CsvException e) {
      throw new RuntimeException();
    }

    try {
      csvReader.close();
    } catch (IOException e) {
      throw new RuntimeException();
    }
    // 예외를 어떻게 처리 할지 모르겠어서 일단 프로그램이 터지도록 했습니다.

    return rows.stream().map(this::generateVoucherFrom).collect(Collectors.toList());
  }

  @Override
  public Voucher saveVoucher(Voucher voucher) {
    CSVWriter csvWriter = getCSVWriter();
    voucher.assignId(generateId());
    String[] newRow = generateRowFrom(voucher);
    csvWriter.writeNext(newRow);

    try {
      csvWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return voucher;
  }

  private UUID generateId() {
    return UUID.randomUUID();
  }

  private String[] generateRowFrom(Voucher voucher) {
    String[] row = new String[2];
    row[COL_UUID] = voucher.getId().toString();
    row[COL_VOUCHER_TYPE] = voucher.getVoucherTypeInString();
    return row;
  }

  private Voucher generateVoucherFrom(String[] row) {
    String voucherType = row[COL_VOUCHER_TYPE];
    VoucherPolicy voucherPolicy = VoucherPolicy.of(voucherType);

    String uuid = row[COL_UUID];
    UUID id = UUID.fromString(uuid);

    Voucher voucher = new Voucher(voucherPolicy);
    voucher.assignId(id);

    return voucher;
  }


  private CSVWriter getCSVWriter() {
    try {
      return new CSVWriter(new FileWriter(this.filePath, true));
    } catch (IOException e) {
      throw new RuntimeException(e);
      // 예외를 어떻게 처리 할지 모르겠어서 일단 프로그램이 터지도록 했습니다.
    }
  }

  private CSVReader getCSVReader() {
    try {
      return new CSVReader(new FileReader(this.filePath));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
      // 예외를 어떻게 처리 할지 모르겠어서 일단 프로그램이 터지도록 했습니다.
    }
  }

}
