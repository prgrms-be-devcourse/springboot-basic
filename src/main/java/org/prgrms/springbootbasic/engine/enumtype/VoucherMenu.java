package org.prgrms.springbootbasic.engine.enumtype;

import org.prgrms.springbootbasic.engine.VoucherFunction;

import java.util.Arrays;
import java.util.Optional;

public enum VoucherMenu {
    EXIT {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            return true;
        }
    },
    CREATE {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.showCreateMenu();
            return false;
        }
    },
    LIST {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.showListMenu();
            return false;
        }
    },
    ALLOCATE {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.allocateVoucherToCustomer();
            return false;
        }
    },
    DEALLOCATE {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.deallocateVoucherFromCustomer();
            return false;
        }
    },
    SEARCH {
        @Override
        public Boolean runMethod(VoucherFunction voucherFunction) {
            voucherFunction.showSearchMenu();
            return false;
        }
    };

    public abstract Boolean runMethod(VoucherFunction voucherFunction);

}
