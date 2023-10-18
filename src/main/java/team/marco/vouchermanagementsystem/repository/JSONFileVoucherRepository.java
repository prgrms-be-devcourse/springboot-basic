package team.marco.vouchermanagementsystem.repository;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.dto.JSONVoucherDTO;
import team.marco.vouchermanagementsystem.model.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;
import team.marco.vouchermanagementsystem.util.FileManager;
import team.marco.vouchermanagementsystem.util.JSONFileManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Primary
public class JSONFileVoucherRepository implements VoucherRepository, DisposableBean {
    FileManager<JSONVoucherDTO> fileManager = new JSONFileManager<>(JSONVoucherDTO.class);
    Map<UUID, Voucher> voucherMap = new HashMap<>();

    public JSONFileVoucherRepository() {
        List<JSONVoucherDTO> loadedDTO = fileManager.load();

        loadedDTO.forEach(dto -> {
            Voucher voucher = switch (dto.getType()) {
                case FIXED -> new FixedAmountVoucher(dto.getId(), dto.getData());
                case PERCENT -> new PercentDiscountVoucher(dto.getId(), dto.getData());
            };

            voucherMap.put(voucher.getId(), voucher);
        });
    }

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getId(), voucher);
        fileManager.save(JSONVoucherDTO.convert(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        return voucherMap.values().stream().toList();
    }

    @Override
    public void destroy() {
        fileManager.close();
    }
}
