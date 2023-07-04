package com.prgrms.commandLineApplication.voucher.discount.validator;

import com.prgrms.commandLineApplication.voucher.discount.Discount;
import com.prgrms.commandLineApplication.voucher.discount.FixedDiscount;
import com.prgrms.commandLineApplication.voucher.discount.PercentDiscount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(DiscountValidator.class);

  private static final String INVALID_DISCOUNT_ERROR = "Invalid Discount";
  private static final String INVALID_FIXED_AMOUNT_RANGE_ERROR = "Invalid discount amount range (0 ~ 10000)";
  private static final String INVALID_PERCENT_AMOUNT_ERROR = "Invalid discount amount range (0 ~ 100)";

  public static void checkDiscount(Discount discount) {
    if (discount == null) {
      LOGGER.error("Discount Error Message => {}", INVALID_DISCOUNT_ERROR);
      throw new IllegalArgumentException(INVALID_DISCOUNT_ERROR);
    }
  }

  public static void checkFixedDiscount(int discountAmount) {
    if (discountAmount < FixedDiscount.MINIMUM_VALUE || discountAmount > FixedDiscount.MAXIMUM_VALUE) {
      LOGGER.error("Discount Error Message => {}", INVALID_FIXED_AMOUNT_RANGE_ERROR);
      throw new IllegalArgumentException(INVALID_FIXED_AMOUNT_RANGE_ERROR);
    }
  }

  public static void checkPercentDiscount(int discountAmount) {
    if (PercentDiscount.PERCENT_RATE_BASE < discountAmount || discountAmount < PercentDiscount.MINIMUM_VALUE) {
      LOGGER.error("Discount Error Message => {}", INVALID_PERCENT_AMOUNT_ERROR);
      throw new IllegalArgumentException(INVALID_PERCENT_AMOUNT_ERROR);
    }
  }

}
