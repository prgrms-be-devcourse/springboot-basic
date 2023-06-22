package prgms.spring_week1.io;

import prgms.spring_week1.Menu.Message.MenuSelectMessage;
import prgms.spring_week1.Menu.Message.VoucherTypeSelectMessage;
import prgms.spring_week1.domain.voucher.model.Voucher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Console implements Input,Output{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public String inputTextOption() throws IOException {
        return br.readLine();
    }

    @Override
    public String inputVoucherType() throws IOException {
        return br.readLine();
    }

    @Override
    public void printMenuList() {
        System.out.println(MenuSelectMessage.selectMessage);
    }

    @Override
    public void printTypeSelectMessage() {
        System.out.println(VoucherTypeSelectMessage.VoucherTypeSelectMessage);
    }

    @Override
    public void printAllVoucher(List<Voucher> voucherList) {
        voucherList.stream().forEach(v -> System.out.println(v));
    }

    @Override
    public void printEmptyListMessage() {
        System.out.println("empty list");
    }
}
