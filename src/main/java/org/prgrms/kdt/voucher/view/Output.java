package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.dto.VoucherDto;

import java.util.List;
import java.util.stream.Stream;

public interface Output {

    void showMenu(Stream<Menu> menuStream);
    void showVoucherList(List<VoucherDto> voucherDtoList);
}
