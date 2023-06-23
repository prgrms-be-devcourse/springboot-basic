package com.programmers.springweekly.repository;

import com.programmers.springweekly.domain.FixedAmountVoucher;
import com.programmers.springweekly.domain.PercentDiscountVoucher;
import com.programmers.springweekly.domain.Voucher;
import com.programmers.springweekly.dto.ReadVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {

    private final String FILE_PATH = "./file/voucherList.csv";
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Override
    public void saveVoucher(Voucher voucher) {
      try {
          BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));

          String voucherId = String.valueOf(voucher.getVoucherId());
          String voucherAmount = String.valueOf(voucher.getVoucherAmount());

          bufferedWriter.write(voucherId);
          bufferedWriter.write(",");
          bufferedWriter.write(voucherAmount);
          bufferedWriter.write(",");
          bufferedWriter.write(voucher.getVoucherType());
          bufferedWriter.newLine();

          bufferedWriter.flush();
          bufferedWriter.close();

      } catch(Exception e){
          logger.error("error message: {}", e.getMessage());
      }
    }

    @Override
    public Map<UUID, Voucher> getVoucherMap() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(FILE_PATH));
            String line = "";

            while((line = bufferedReader.readLine()) != null){
                String[] readLine = line.split(",");

                ReadVoucherDto readVoucherDto = new ReadVoucherDto(readLine[0], readLine[1], readLine[2]);

                Voucher voucher = createVoucher(readVoucherDto);

                voucherMap.put(readVoucherDto.getVoucherId(), voucher);
            }

        } catch (Exception e){
            logger.error("error message: {}", e.getMessage());
        }

        return voucherMap;
    }

    private Voucher createVoucher(ReadVoucherDto readVoucherDto){
        if(readVoucherDto.getVoucherType().equals("fixed")){
            return new FixedAmountVoucher(readVoucherDto.getVoucherId(),readVoucherDto.getDiscountAmount(), readVoucherDto.getVoucherType());
        }

        return new PercentDiscountVoucher(readVoucherDto.getVoucherId(),readVoucherDto.getDiscountAmount(), readVoucherDto.getVoucherType());
    }
}
