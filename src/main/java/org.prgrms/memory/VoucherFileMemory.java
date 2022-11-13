package org.prgrms.memory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class VoucherFileMemory implements Memory {

  private final File file;

  public VoucherFileMemory(@Value("${file.voucher.file-path}") String filePath) {
    this.file = new File(filePath);
  }

  public Voucher save(Voucher voucher) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    writer.write(voucher.toString());
    writer.close();
    return voucher;
  }


  public List<String> findAll() throws IOException {
    List<String> voucherList = new ArrayList<>();

    if (file.exists()) {
      BufferedReader reader = new BufferedReader(new FileReader(file));

      String line;

      while ((line = reader.readLine()) != null) {
        voucherList.add(line);
      }
      reader.close();
    }
    return voucherList;
  }

}
