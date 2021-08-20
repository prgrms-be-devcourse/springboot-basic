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

    public void insertVoucher(Voucher voucher){
        voucherRepository.insert(voucher);
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
            .findById(voucherId)
            .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public StringBuilder showVouchers(){
        Map<UUID, Voucher> findVouchers = voucherRepository.findAll();
        StringBuilder sb = new StringBuilder();
        for (Voucher voucher : findVouchers.values()) {
            sb.append(voucher.toString());
            sb.append("\n");
        }
        return sb;
    }



    //public void join(Member member) {
        //memberRepository.save(member);
    //}
}
