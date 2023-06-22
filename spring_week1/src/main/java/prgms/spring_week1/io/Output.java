package prgms.spring_week1.io;

import prgms.spring_week1.domain.voucher.model.Voucher;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Output {
    public void printMenuList();
    public void printTypeSelectMessage();
    public void printAllVoucher(List<Voucher> voucherList);
    public void printEmptyListMessage();
}
