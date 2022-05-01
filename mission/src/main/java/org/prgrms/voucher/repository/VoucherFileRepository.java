package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("file")
public class VoucherFileRepository implements VoucherRepository {

    private final String voucherFilePath;
    private final String idSequencePath;

    public VoucherFileRepository(@Value("${voucher.path}") String voucherFilePath,
                                 @Value("${sequence.path}") String idSequencePath
    ) {

        this.voucherFilePath = voucherFilePath;
        this.idSequencePath = idSequencePath;
    }

    @Override
    public Voucher save(Voucher voucher) {

        if (voucher == null) {
            throw new IllegalArgumentException("Voucher is null");
        }

        if (voucher.getVoucherId() == null) {
            Voucher voucherEntity = setIdVoucher(voucher);

            String saveFile = voucherEntity.getVoucherId() + ", "
                    + voucherEntity.getDiscountValue() + ", "
                    + voucherEntity.getVoucherType() + ", "
                    + voucherEntity.getCreatedAt() + ", "
                    + voucherEntity.getUpdatedAt();
            FileUtils.saveEntity(saveFile, voucherFilePath);
            FileUtils.saveIdSequence(voucherEntity.getVoucherId(), idSequencePath);

            return voucherEntity;
        }

        throw new IllegalArgumentException("is not new Voucher..");
    }

    @Override
    public List<Voucher> findAll() {

        return FileUtils.readEntities(voucherFilePath)
                .stream()
                .map(v -> v.split(", "))
                .map(v -> VoucherType.valueOf(v[2])
                        .createVoucher(
                                Long.parseLong(v[0]),
                                Long.parseLong(v[1]),
                                VoucherType.valueOf(v[2]),
                                LocalDateTime.parse(v[3]),
                                LocalDateTime.parse(v[4])
                        )
                )
                .toList();
    }

    private Voucher setIdVoucher(Voucher voucher) {

        Long lastId = FileUtils.getLastId(idSequencePath);

        return voucher.getVoucherType().createVoucher(
                IdGenerator.fileIdGenerate(lastId),
                voucher.getDiscountValue(),
                voucher.getVoucherType(),
                voucher.getCreatedAt(),
                voucher.getUpdatedAt()
        );
    }
}