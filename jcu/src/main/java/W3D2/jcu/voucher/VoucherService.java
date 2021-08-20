package W3D2.jcu.voucher;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    
    // Voucher추가
    public void insertVoucher(Voucher voucher){
        voucherRepository.insert(voucher);
    }
    
    // 수업내용인데 사용하지는 않았습니다.
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
            .findById(voucherId)
            .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }
    
    // UUID를 그대로 받아오면 안되기 때문에 Voucher정보만 toString을 이용하여 StringBuilder로 반환
    public StringBuilder showVouchers(){
        Map<UUID, Voucher> findVouchers = voucherRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (Voucher voucher : findVouchers.values()) {
            sb.append(voucher.toString());
            sb.append("\n");
        }
        return sb;
    }

}
