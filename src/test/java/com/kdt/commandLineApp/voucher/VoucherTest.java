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
@ActiveProfiles("dev")
class VoucherTest {
    @Test
    public void createVoucher1() {
        try {
            Voucher voucher = new Voucher("fixed",1000f);
            Float discountPrice = 0f;

            discountPrice = voucher.discount(10000);

            assertThat(voucher.getType() , is("fixed"));
            assertThat(voucher.getDiscountAmount() , is(1000f));
            assertThat(discountPrice, is(9000f));
            assertThrows(CanNotDiscountException.class, ()-> {
                voucher.discount(900);
            });
        }
        catch (Exception e) {

        }
    }

    @Test
    public void createVoucher2() {
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("fix",1000f);
        });
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("fixed",-1f);
        });
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("percent",-1f);
        });
        assertThrows(WrongVoucherParamsException.class,()-> {
            new Voucher("percent",101f);
        });
    }

    @Test
    void discount() {
        try {
            Voucher voucher = new Voucher("fixed",1000f);
            Float discountPrice = 0f;

            discountPrice = voucher.discount(10000);

            assertThat(voucher.getType() , is("fixed"));
            assertThat(voucher.getDiscountAmount() , is(1000f));
            assertThat(discountPrice, is(9000f));
            assertThrows(CanNotDiscountException.class, ()-> {
                voucher.discount(900);
            });
        }
        catch (Exception e) {

        }
    }
}