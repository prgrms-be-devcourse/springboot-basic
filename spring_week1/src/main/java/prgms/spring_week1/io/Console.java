package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Console implements Input,Output{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final String NEW_LINE = "\n";
    private final String printMenuListMessage = "=== Voucher Program ==="+ NEW_LINE +
            "Type exit to exit the program."+ NEW_LINE +
            "Type create to create a new voucher."+ NEW_LINE +
            "Type list to list all vouchers."+NEW_LINE+
            "Type black to list all blackList";
    private final String printTypeSelectMessage = " === Voucher Select ==="+ NEW_LINE +
            "Fixed Amount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> FixedAmountVoucher" + NEW_LINE +
            "Percent Discount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> PercentDiscountVoucher";

    @Override
    public String inputTextOption(){
        try {
            return br.readLine();
        }catch (IOException e){
            printWrongMenuMessage();
            return null;
        }
    }

    @Override
    public String inputVoucherType(){
        try {
            return br.readLine();
        }catch (IOException e){
            printWrongMenuMessage();
            return null;
        }
    }

    @Override
    public Long insertDiscountAmountVoucher() {
        boolean IS_VALID_AMOUNT = true;
        try {
            printInsertFixedVoucherMessage();
            long discountAmount = Long.parseLong(br.readLine());
            while(IS_VALID_AMOUNT){
                if(discountAmount > 0) {
                    break;
                }
                printInsertFixedVoucherMessage();
                discountAmount = Long.parseLong(br.readLine());


            }
            return discountAmount;

        }catch (IOException e){
            printWrongMenuMessage();
            return null;
        }
    }

    @Override
    public int insertDiscountPercentVoucher() {
        boolean IS_VALID_AMOUNT = true;
        try {
            printInsertPercentVoucherMessage();
            int discountAmount = Integer.parseInt(br.readLine());
            while(IS_VALID_AMOUNT){
                if(discountAmount > 0 && discountAmount <= 100d) {
                    break;
                }
                printInsertPercentVoucherMessage();
                discountAmount = Integer.parseInt(br.readLine());

                System.out.println("error");
            }
            return discountAmount;

        }catch (IOException e){
            printWrongMenuMessage();
            return 0;
        }
    }

    @Override
    public void printMenuList() {
        System.out.println(printMenuListMessage);
    }

    @Override
    public void printTypeSelectMessage() {
        System.out.println(printTypeSelectMessage);
    }

    @Override
    public void printAllVoucher(List<Voucher> voucherList) {
        voucherList.stream().forEach(v -> System.out.println(v));
    }

    @Override
    public void printEmptyListMessage() {
        System.out.println("조회된 바우처 리스트가 없습니다.");
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

    @Override
    public void printBlackConsumerList(List<BlackConsumer> blackConsumerList) {
        blackConsumerList.forEach(bl -> System.out.println(bl.getName() + " " + bl.getAge()));
    }

    @Override
    public void printNoSuchVoucherType() {
        System.out.println("해당 바우처 타입이 존재하지 않습니다.");
    }
}
