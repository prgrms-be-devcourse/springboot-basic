package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;


@Service
public class VoucherService {
    // Repository는 Service에서 사용, 관리함
    private final VoucherRepository voucherRepository;
    // voucher 파일 저장 경로
    private final String filePath = "C:/Users/NB1/Desktop/PROGRAM/GitWorkSpace/Programmers_Devcourse/w3-SpringBoot_Part_A/kdt-spring-order/";
    private final String fileName = "voucher_list.csv";
    File file = new File(filePath + fileName);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId){
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }


    // 사용자가 입력한 type에 맞는 voucher 생성
    public void createVoucher(UUID voucherId, VoucherType voucherType, long value){
        if(voucherType == VoucherType.fixed){
            voucherRepository.insert(new FixedAmountVoucher(voucherId, value));
        }
        else if(voucherType == VoucherType.percent){
            voucherRepository.insert(new PercentDiscountVoucher(voucherId, value));
        }
    }

    // voucher 리스트 -> controller로 반환
    public List<Voucher> getVoucherList(){
        return voucherRepository.getVoucherList();
    }

    //
    public void loadVoucherList(){
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
                createVoucher(voucherId, voucherType, voucherValue);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //
    public void saveVoucherList(){
        List<Voucher> voucherList = voucherRepository.getVoucherList();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file));){
            // 컬럼명 먼저 써주고
            bw.write("ID,VALUE,TYPE");
            // 리스트 순회해서 한줄씩 추가
            for(Voucher voucher : voucherList){
                bw.write(System.lineSeparator());
                String row = voucher.getVoucherId()
                        + "," + voucher.getVoucherValue()
                        + "," + voucher.getVoucherType();

                bw.write(row);
            }
            // 저장
            bw.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
