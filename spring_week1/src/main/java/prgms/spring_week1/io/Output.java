package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;
import java.util.List;


public interface Output {
    void printMenuList();
    void printTypeSelectMessage();
    void printAllVoucher(List<Voucher> voucherList);
    void printEmptyListMessage();
    void printWrongMenuMessage();
    void printInsertFixedVoucherMessage();
    void printInsertPercentVoucherMessage();
    void printInsertVoucherInfo(Voucher voucher);
    void printBlackConsumerList(List<BlackConsumer> blackConsumerList);
}
