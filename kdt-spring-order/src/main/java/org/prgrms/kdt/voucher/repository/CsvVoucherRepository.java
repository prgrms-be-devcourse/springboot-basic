package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile("csv")
public class CsvVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Value("${csv.file-path}")
    private String filePath;

    @Value("${csv.voucher-repository.file-name}")
    private String fileName;

    private String basePath = System.getProperty("user.dir");;
    private String absoluteFilePath;

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
    public void loadCsv() throws IOException {
        absoluteFilePath = basePath + filePath + fileName;

        try(BufferedReader br = new BufferedReader(new FileReader(absoluteFilePath))){
            String row = "";
            br.readLine();
            while((row = br.readLine()) != null){
                String[] dummyArr = row.split(",");
                // 모두 파싱해준다~
                UUID voucherId = UUID.fromString(dummyArr[0]);
                long voucherValue = Long.parseLong(dummyArr[1]);
                VoucherType voucherType = VoucherType.convert(dummyArr[2]);
                // 파싱결과로 바우처 생성하기
                switch(voucherType){
                    case FIXED -> insert(new FixedAmountVoucher(voucherId, voucherValue));
                    case PERCENT -> insert(new PercentDiscountVoucher(voucherId, voucherValue));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Bean 파괴시 파일 저장
    @PreDestroy
    public void saveCsv() throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(absoluteFilePath))){
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
