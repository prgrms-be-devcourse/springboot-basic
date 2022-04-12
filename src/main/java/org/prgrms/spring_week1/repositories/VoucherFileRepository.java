package org.prgrms.spring_week1.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.LoggerFinder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.spring_week1.models.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class VoucherFileRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);
    private static final File file = new File("../vouchers.csv");

    @Override
    public void insert(Voucher voucher) {
        StringBuilder sb = new StringBuilder();
        BufferedWriter bw = null;

        try{
            bw = new BufferedWriter(new FileWriter("vouchers.csv", true));

            // UUID,voucher.toString() 형식으로 작성
            sb.append(voucher.getVoucherId());
            sb.append(",");
            sb.append(voucher.toString());
            bw.write(sb.toString());
            bw.newLine(); // 개행

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();

        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> getAllVoucher() {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        String line = " ";
        List<String> vouchers = new ArrayList<>();

        try{
            br = new BufferedReader(new FileReader("vouchers.csv"));
            while((line = br.readLine()) != null){
                String[] strings = line.split(",");
                vouchers.add(strings[1]); // voucher.toString()만 slicing
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close(); // 사용 후 BufferedReader를 닫기
                }
            } catch(IOException e) {
                e.printStackTrace();
            }

        }

        return vouchers;
    }
}
