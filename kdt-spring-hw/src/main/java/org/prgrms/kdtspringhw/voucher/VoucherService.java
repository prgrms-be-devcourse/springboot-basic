package org.prgrms.kdtspringhw.voucher;

import org.prgrms.kdtspringhw.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {
    //String RepositoryMode = environment.getProperty("kdt.version");

    private final VoucherRepository voucherRepository  ;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    // 오토 -> 빈의 찾아서 하나의 필드를 직접 주입한다.
    // 생성자 -> 직접 주입을 해준다.

    //setter를 통해서 의존관계 주입이 가능하기도 함
//    @Autowired
//    public void setVoucherRepository(VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//    }


    // String minimumOrderAmount = environment.getProperty("mode");

    // 생성자를 통해서 의존관계가 형성되도록 하는게 좋음 -> 초기화시에 필요한 모든 의존관계가 형성되기 때문에 안전, 잘못된 패턴을 찾을 수 있게 도와줌, 테스트를 쉽게 해줌, 불변성을 확보
//    @Autowired// 생성자를 통해서 의존성을 주입하게 될때 자동으로 주입될 Bean이 여러개 일때 Bean주입을 설정해 주어야함.
//    public VoucherService( VoucherRepository voucherRepository) {
//        this.voucherRepository = voucherRepository;
//        //voucherRepository.roadCSV();
//    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(()->new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }


    public void createVoucher(String type){
        Voucher voucher ;
        if(type.equals("fix")){
            voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        }
        else if(type.equals("per")){
            voucher = voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10));
        }
    }

    public Map<UUID,Voucher> getVouchers() {return voucherRepository.returnAll();};


}
