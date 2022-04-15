package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.repository.io.FileInput;
import com.voucher.vouchermanagement.repository.io.FileOutput;
import com.voucher.vouchermanagement.repository.utils.CsvDeserializer;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class VoucherFileRepository implements VoucherRepository {

  private final File voucherDb;
  private final FileOutput output;
  private final FileInput input;
  private final CsvDeserializer<Voucher> voucherDeserializer;
  private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

  public VoucherFileRepository(
      @Qualifier("voucherDb") File voucherDb, FileOutput output, FileInput input,
      @Qualifier("voucherDeserializer") CsvDeserializer<Voucher> voucherDeserializer) {
    this.voucherDb = voucherDb;
    this.output = output;
    this.input = input;
    this.voucherDeserializer = voucherDeserializer;
  }

  @Override
  public void save(Voucher voucher) throws IOException {
    String dataString = voucher.getVoucherType() + ", " +
        voucher.getVoucherId() + "," +
        voucher.getValue() + "," +
        voucher.getCreatedAt();

    output.writeln(voucherDb, dataString);
  }

  @Override
  public List<Voucher> findAll() throws IOException {
    List<String> foundDataStrings = input.readAllLine(voucherDb);

    return foundDataStrings
        .stream()
        .map(voucherDeserializer::deserialize)
        .collect(Collectors.toList());
  }

}
