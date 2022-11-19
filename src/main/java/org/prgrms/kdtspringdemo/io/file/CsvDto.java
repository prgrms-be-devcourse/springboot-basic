package org.prgrms.kdtspringdemo.io.file;

import org.prgrms.kdtspringdemo.domain.customer.model.BlackCustomer;
import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;

import java.util.ArrayList;
import java.util.List;

public class CsvDto {
    public final List<String[]> value;

    private CsvDto(List<String[]> value) {
        this.value = value;
    }

    public static CsvDto from(List<String[]> value) {
        return new CsvDto(value);
    }

    public static CsvDto from(BlackCustomer customer) {
        List<String[]> rowDatas = new ArrayList<>();
        String[] rowData = {customer.getCustomerId().toString(), customer.getEmail(), customer.getBirth().toString()};
        rowDatas.add(rowData);
        return CsvDto.from(rowDatas);
    }

    public static CsvDto from(Voucher voucher) {
        List<String[]> lines = new ArrayList<>();
        String[] line = {voucher.getVoucherId().toString(), String.valueOf(voucher.getValue()), voucher.getVoucherType().name()};
        lines.add(line);
        return CsvDto.from(lines);
    }
}
