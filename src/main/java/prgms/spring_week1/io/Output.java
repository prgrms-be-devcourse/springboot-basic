package prgms.spring_week1.io;

import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;

import java.util.List;

@Component
public class Output {
    private final String printMenuListMessage =
            """
                    === Voucher Program ===
                    Type exit to exit the program.
                    Type create to create a new voucher.
                    Type list to list all vouchers.
                    Type black to list all blackList
                    """;

    private final String printTypeSelectMessage =
            """
                    === Voucher Select ===
                    Fixed Amount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> FixedAmountVoucher       
                    Percent Discount Voucher 을 생성하려면 띄어쓰기 없이 입력하세요. -> PercentDiscountVoucher
                    """;


    public void printMenuList() {
        System.out.println(printMenuListMessage);
    }

    public void printTypeSelectMessage() {
        System.out.println(printTypeSelectMessage);
    }

    public void printAllVoucher(List<Voucher> voucherList) {
        if(voucherList.isEmpty()){
            printEmptyListMessage();
            return;
        }

        for (Voucher voucher : voucherList) {
            switch (voucher.getVoucherType()) {
                case FIXED -> printDiscountFixedVoucherInfo(voucher);
                case PERCENT -> printDiscountAmountVoucherInfo(voucher);
            }
        }
    }

    public void printEmptyListMessage() {
        System.out.println("조회된 바우처 리스트가 없습니다.");
    }

    public void printWrongMenuMessage() {
        System.out.println("해당 메뉴가 존재하지 않습니다.");
    }

    public void printInsertFixedVoucherMessage() {
        System.out.print("할인 가격을 입력하세요 : ");
    }

    public void printInsertPercentVoucherMessage() {
        System.out.print("할인율을 입력하세요 : ");
    }

    public void printInsertVoucherInfo(Voucher voucher) {
        System.out.println("상품권 등록이 완료되었습니다.");
    }

    public void printBlackConsumerList(List<BlackConsumer> blackConsumerList) {
        if(blackConsumerList.isEmpty()){
            printEmptyBlackListMessage();
            return;
        }
        blackConsumerList.forEach(bl -> System.out.println(bl.getName() + " " + bl.getAge()));
    }

    public void printNoSuchVoucherType() {
        System.out.println("해당 바우처 타입이 존재하지 않습니다.");
    }

    public void printDiscountFixedVoucherInfo(Voucher fixedAmountVoucher) {
        System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                "할인 가격 :" + fixedAmountVoucher.getDiscount() + "원");
    }

    public void printDiscountAmountVoucherInfo(Voucher percentDiscountVoucher) {
        System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                "할인률 :" + percentDiscountVoucher.getDiscount() + " 퍼센트");
    }

    public void printEmptyBlackListMessage() {
        System.out.println("블랙리스트 목록이 없습니다.");
    }
}
