package org.prgrms.kdt.voucher.view;

import org.prgrms.kdt.voucher.model.Menu;
import org.prgrms.kdt.voucher.dto.VoucherDto;

import java.util.List;
import java.util.stream.Stream;

public class ConsoleOutput implements Output{

    private static final String defaultProgramMessage = "=== Voucher Program ===";
    private static final String defaultVoucherCategories = "VoucherName" + "           " + "Benefit";

    private static void accept(Menu menu) {
        System.out.println(menu.getMenuExplain());
    }

    private static void accept(VoucherDto voucherDto) {
        System.out.println(voucherDto.getVoucherDtoName() + voucherDto.getBenefit());
    }

    @Override
    public void showMenu(Stream<Menu> menuStream) {
        System.out.println(defaultProgramMessage);
        menuStream.forEach(ConsoleOutput::accept);
    }

    @Override
    public void showVoucherList(List<VoucherDto> voucherDtoList) {
        System.out.println(defaultVoucherCategories);
        voucherDtoList.forEach(ConsoleOutput::accept);
    }
}
