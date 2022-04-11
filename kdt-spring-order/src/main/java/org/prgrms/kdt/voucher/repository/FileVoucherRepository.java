package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.blacklist.domain.Blacklist;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private Input input;
    private Output output;

    // @Value("${datasource.voucher.path}")
    // private String path;
    private final String path = "src/main/resources/datasource/voucher_db.csv";
    private final File file = new File(path);

    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
    public FileVoucherRepository() throws IOException {}

    @Override
    public void insert(Voucher voucher) {
        try {
            bw = new BufferedWriter(new FileWriter(file,true));
            String toWrite = voucher.getVoucherId() + ","
                             + voucher.getVoucherType() + ","
                             + voucher.getDiscountRate() + "\n";
            bw.write(toWrite);
            bw.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            // output.?();
        } catch (IOException e) {
            logger.error(e.getMessage());
            // output.?();
        }
    }

    public void list() {
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                String[] voucherInfo = line.split(",");
                String voucherId = voucherInfo[0];
                String voucherType = voucherInfo[1];
                String discountRate = voucherInfo[3];
                output.printFileVoucherRepo(voucherId, voucherType, discountRate);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
