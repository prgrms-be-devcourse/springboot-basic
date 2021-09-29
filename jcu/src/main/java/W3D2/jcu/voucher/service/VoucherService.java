package W3D2.jcu.voucher.service;

import W3D2.jcu.voucher.model.Voucher;
import W3D2.jcu.voucher.model.VoucherStatus;
import W3D2.jcu.voucher.repository.VoucherRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    // Note : 메소드 오버로딩을 많이 사용하는지
    //  - 사용한다면 어떤 경우에 사용되는지

    public void insertVoucher(Voucher voucher){
        // Todo : null 처리
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
        sb.append("\n<Voucher List>\n");
        for (Voucher voucher : findVouchers.values()) {
            sb.append(voucher.toString());
            sb.append("\n");
        }
        return sb;
    }

    public void loadStorage() throws IOException {
        voucherRepository.readStorage();
    }

    @SneakyThrows
    public void saveStorage(){
        voucherRepository.writeStorage();
    }

}
