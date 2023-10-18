package team.marco.vouchermanagementsystem.dto;

import team.marco.vouchermanagementsystem.model.Voucher;
import team.marco.vouchermanagementsystem.model.VoucherType;

import java.util.UUID;

public class JSONVoucherDTO {
    private VoucherType type;
    private UUID id;
    private int data;

    private JSONVoucherDTO() {
        // for object mapper deserializing
    }

    private JSONVoucherDTO(VoucherType type, UUID id, int data) {
        this.type = type;
        this.id = id;
        this.data = data;
    }

    public static JSONVoucherDTO convert(Voucher voucher) {
        return new JSONVoucherDTO(voucher.getType(), voucher.getId(), voucher.getData());
    }

    public VoucherType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public int getData() {
        return data;
    }
}
