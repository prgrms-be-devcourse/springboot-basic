package org.prms.repository;

import org.prms.domain.FixedAmountVoucher;
import org.prms.domain.PercentDiscountVoucher;
import org.prms.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
//@Primary
@Qualifier("fileRepo")
public class FileRepository implements VoucherRepository {


    private ConcurrentHashMap<UUID, Voucher> map = new ConcurrentHashMap<>();

    private static final File files = new File(".\\java_Text.txt");

    FileWriter fw_append;

    //    FileInputStream input;
//    @Autowired
//    public FileRepository() throws IOException {
//
////        String str="test";
////        bs.write(str.getBytes());
//
//    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }


    @Override
    public void insert(Voucher voucher) {

        try {

            // 파일에 저장
            fileSave(voucher);


        } catch (Exception e) {
            e.getStackTrace();
            // TODO: handle exception
        }

    }

    public void fileSave(Voucher voucher) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            fw_append = new FileWriter(".\\java_Text.txt", true); // 파일이 있을경우 이어쓰기

            sb.append("ID:" + voucher.getVoucherId() + "\t");
            sb.append("TYPE:" + voucher.getType() + "\t");
            sb.append("AMOUNT:" + voucher.getAmount() + "\t");
            System.out.println(sb.toString());
            fw_append.write(sb.toString() + "\r\n");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fw_append.close();
        }


    }


    @Override
    public ConcurrentHashMap<UUID, Voucher> getList() {
        fileRead();
        return map;
    }

    public void fileRead() {
        StringBuilder sb = new StringBuilder();

        if (files.exists()) {
            try {
                BufferedReader br=new BufferedReader(new FileReader(".\\java_Text.txt"));
                String sLine=null;
                while ((sLine=br.readLine())!=null) {
//                  System.out.println(sLine);
                    parsing(sLine);
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
            finally {
            }

        }

    }

    public void parsing(String line){
        String []temp=line.split(":");
        String id=temp[1].split("\t")[0];
        String type=temp[2].split("\t")[0].toLowerCase();
        Long price=Long.parseLong(temp[3].split("\t")[0]);

        Voucher voucher;
        if (type.equals("fixed")) {
            voucher=new FixedAmountVoucher(UUID.fromString(id),price);
            map.put(voucher.getVoucherId(),voucher);
        }
        else {
            voucher=new PercentDiscountVoucher(UUID.fromString(id),price);
            map.put(voucher.getVoucherId(),voucher);
        }


    }

}
