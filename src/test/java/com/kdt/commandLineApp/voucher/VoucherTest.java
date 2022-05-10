package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.AppContext;
import com.kdt.commandLineApp.exception.CanNotDiscountException;
import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(classes = {AppContext.class})
@ActiveProfiles("db")
class VoucherTest {
    @Test
    public void createVoucher1() {
        try {
            Voucher voucher1 = new Voucher("fixed",1000);

            assertThat(voucher1.getType() , is("fixed"));
            assertThat(voucher1.getDiscountAmount() , is(1000));

            Voucher voucher2 = new Voucher("percent",100);

            assertThat(voucher2.getType() , is("percent"));
            assertThat(voucher2.getDiscountAmount() , is(100));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createVoucher2() {
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("fix",1000);
        });
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("fixed",-1);
        });
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("percent",-1);
        });
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("percent",101);
        });
    }

    @Test
    void discount1() {
        try {
            Voucher voucher = new Voucher("fixed",1000);

            Float discountPrice = voucher.discount(10000);

            assertThat(voucher.getType() , is("fixed"));
            assertThat(voucher.getDiscountAmount() , is(1000));
            assertThat(discountPrice, is(9000f));
            assertThrows(CanNotDiscountException.class, ()-> {
                voucher.discount(900);
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void discount2() {
        try {
            Voucher voucher = new Voucher("percent",10);

            Float discountPrice = voucher.discount(10000);

            assertThat(voucher.getType() , is("percent"));
            assertThat(voucher.getDiscountAmount() , is(10));
            assertThat(discountPrice, is(9000f));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}