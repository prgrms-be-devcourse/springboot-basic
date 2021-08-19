package org.prgrms.kdt.voucher;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Qualifier("csv") // 사용할때 해당 키워드를 이용해서 주입가능
public class CsvVoucherRepository implements VoucherRepository{

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(),voucher);
        return voucher;
    }

    @Override
    public Map<UUID, Voucher> returnAll() {
        return storage;
    }


    public void roadCSV() {
        BufferedReader br = null;
        try{
            br = Files.newBufferedReader(Paths.get("/Users/minkyujeon/Desktop/PJ/programers/spring/w3-SpringBoot_Part_A/kdt-spring-order/repository/voucer.csv"));
            String line = "";

            while((line = br.readLine()) != null){
                Voucher voc;
                String[] token = line.split(",",-1);
                //for (int i = 0; i <5 ; i++) {
                    String vouchertype = token[0];
                    if( vouchertype.equals("fix")){
                        voc = new FixedAmountVoucher(UUID.fromString(token[1]),Long.parseLong(token[2]));
                        storage.put(voc.getVoucherId(),voc);
                    }
                    else if ( vouchertype.equals("per")){
                        voc = new PercentDiscountVoucher(UUID.fromString(token[1]),Long.parseLong(token[2]));
                        storage.put(voc.getVoucherId(),voc);
                    }
                //}
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCSV(){
        BufferedWriter bw = null;
        System.out.println("혹시여기?");
        try{
            bw = Files.newBufferedWriter(Paths.get("/Users/minkyujeon/Desktop/PJ/programers/spring/w3-SpringBoot_Part_A/kdt-spring-order/repository/voucer.csv"), Charset.forName("UTF-8"));

            System.out.println("여기인것인가");
            for(UUID uuid : storage.keySet()){
                Voucher voc;
                voc = storage.get(uuid);
                System.out.println("저장시작하는구문");
                if (voc instanceof FixedAmountVoucher) {
                    System.out.println("저장~");
                    bw.write("fix,"+voc.getVoucherId()+","+((FixedAmountVoucher) voc).getAmount());
                    bw.newLine();
                }
                else if ( voc instanceof PercentDiscountVoucher){
                    bw.write("per,"+voc.getVoucherId()+","+((PercentDiscountVoucher) voc).getPercent());
                    bw.newLine();
                }
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(bw!=null){
                    bw.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void postConstruct(){
        roadCSV();
    }
    @PreDestroy
    public void preDestory(){
        writeCSV();
        //System.out.println("preDestory called!");
    }
}
