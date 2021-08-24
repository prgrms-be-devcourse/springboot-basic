package org.prgrms.kdt.Voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("prod")
//@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository{

    //bean 설정할때 만들어줘서 경로를 private final하게 선언하게 되면 널값이 들어간다.
    @Value("${voucher-list.path}")
    String filePath;


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher)  {
        BufferedWriter bw;
        String aData;


        String Filepath = System.getProperty("user.dir") +"/"+ filePath;

        try{
            bw = new BufferedWriter(new FileWriter(Filepath, true));
            aData = voucher.getVoucherId() + "," + voucher.getVoucherdiscount() + "," + voucher.getType();
            bw.write(aData);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
      return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        BufferedReader br = null;
        String line = "";
        List<Voucher> vouchers = new ArrayList<>();
        String Filepath = System.getProperty("user.dir") +"/"+ filePath;

        try {
            br = new BufferedReader(new FileReader(Filepath));
            while ((line = br.readLine()) != null) {
                String[] array = line.split(",");
                UUID id = UUID.fromString(array[0]);
                long amount = Long.parseLong(array[1]);
                if (TypeStatus.valueOf(array[2]) == TypeStatus.Fixed) {
                    vouchers.add(new FixedAmountVoucher(id, amount));
                } else vouchers.add(new PercentDiscountVoucher(id, amount));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vouchers;

    }
}
