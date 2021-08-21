package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Qualifier("csv")
public class CsvVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    // voucher 파일 저장 경로
    private final String filePath = "C:/Users/NB1/Desktop/PROGRAM/GitWorkSpace/Programmers_Devcourse/w3-SpringBoot_Part_A/kdt-spring-order/";
    private final String fileName = "voucher_list.csv";
    File file = new File(filePath + fileName);

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> getVoucherList() {
        return new ArrayList<>(storage.values());
    }

    // Bean 생성시 파일 불러오기
    @PostConstruct
    public void loadCsv() {
        try(BufferedReader br = new BufferedReader(new FileReader(file));){
            String row = "";
            br.readLine();
            while((row = br.readLine()) != null){
                String[] dummyArr = row.split(",");
                // 모두 파싱해준다~
                UUID voucherId = UUID.fromString(dummyArr[0]);
                long voucherValue = Long.parseLong(dummyArr[1]);
                VoucherType voucherType = VoucherType.valueOf(dummyArr[2]);
                // 파싱결과를 생성 메소드에 넣기~
                if(voucherType == VoucherType.fixed){
                    insert(new FixedAmountVoucher(voucherId, voucherValue));
                }
                else if(voucherType == VoucherType.percent){
                    insert(new PercentDiscountVoucher(voucherId, voucherValue));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Bean 파괴시 파일 저장
    @PreDestroy
    public void saveCsv() {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file));){
            // 컬럼명 먼저 써주고
            bw.write("ID,VALUE,TYPE");
            // 리스트 순회해서 한줄씩 추가
            for(Voucher voucher : storage.values()){
                bw.write(System.lineSeparator());
                String row = voucher.getVoucherId()
                        + "," + voucher.getVoucherValue()
                        + "," + voucher.getVoucherType();
                bw.write(row);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
