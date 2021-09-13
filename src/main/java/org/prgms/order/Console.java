package org.prgms.order;

import org.prgms.order.io.Input;
import org.prgms.order.io.Output;
import org.prgms.order.voucher.service.VoucherService;

import java.util.Scanner;

 public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public String input() {
        return scanner.nextLine();
    }


    @Override
    public void mainMenu() {
        System.out.println("\n=== Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type voucher to voucher program.");
        System.out.println("Type customer to customer program.");
        System.out.println("Type wallet to wallet program.");
        System.out.print(":: ");
    }

     @Override
     public void voucherMenu() {
         System.out.println("\n=== Voucher Program ===");
         System.out.println("Type create to create a new voucher.");
         System.out.println("Type list to voucher list.");
         System.out.println("Type expiry to set voucher expiry date.");
         System.out.print(":: ");
     }

     @Override
     public void customerMenu() {
         System.out.println("\n=== Customer Program ===");
         System.out.println("Type create to create a new consumer.");
         System.out.println("Type list to list all consumer.");
         System.out.println("Type blackList to list all black consumers.");
         System.out.print(":: ");
     }

     @Override
     public void walletMenu() {
         System.out.println("\n=== Wallet Program ===");
         System.out.println("Type create to create a new wallet.");
         System.out.println("Type customer to list customer Wallet.");
         System.out.println("Type voucher to list voucher Wallet.");
         System.out.print(":: ");
     }




     @Override
     public void createVoucherType() {
         System.out.println("\n=== Create Voucher ===");
         System.out.println("Type Fixed to create a new FixedAmountVoucher.");
         System.out.println("Type Percent to create a new PercentDiscountVoucher.");
         System.out.print(":: ");
     }


     @Override
     public void deleteWallet() {
         System.out.println("\nType main to return main");
         System.out.println("Type all to delete all wallet.");
         System.out.println("Type choose to delete choose wallet.");
         System.out.print(":: ");
     }

     @Override
     public void deleteVoucher() {
         System.out.println("\nType main to return main");
         System.out.println("Type all to delete all voucher.");
         System.out.println("Type choose to delete choose voucher.");
         System.out.print(":: ");

     }


 }
