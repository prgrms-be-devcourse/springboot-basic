package prgms.spring_week1.io;

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
    private final String NEW_LINE = "\n";
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
        System.out.println( "=== Voucher Program ==="+ NEW_LINE +
                "Type exit to exit the program."+ NEW_LINE +
                "Type create to create a new voucher."+ NEW_LINE +
                "Type list to list all vouchers.");
    }

    @Override
    public void printTypeSelectMessage() {
        System.out.println(" === Voucher Select ==="+ NEW_LINE +
                "Fixed Amount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> FixedAmountVoucher" + NEW_LINE +
                "Percent Discount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> PercentDiscountVoucher");
    }

    @Override
    public void printAllVoucher(List<Voucher> voucherList) {
        voucherList.stream().forEach(v -> System.out.println(v));
    }

    @Override
    public void printEmptyListMessage() {
        System.out.println("empty list");
    }

    @Override
    public void printWrongMenuMessage() {
        System.out.println("해당 메뉴가 존재하지 않습니다.");
    }

    @Override
    public void printInsertFixedVoucherMessage() {
        System.out.print("할인 가격을 입력하세요 : ");
    }

    @Override
    public void printInsertPercentVoucherMessage() {
        System.out.print("할인율을 입력하세요 : ");
    }

    @Override
    public void printInsertVoucherInfo(Voucher voucher) {
        System.out.println(voucher);
    }


}
