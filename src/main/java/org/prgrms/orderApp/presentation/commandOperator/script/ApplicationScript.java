package org.prgrms.orderApp.presentation.commandOperator.script;


public interface ApplicationScript {
    String startMessage = "=== Voucher Program === \nType exit to exit the program\n" +
                                        "Type created to a new voucher\nType list to list all vouchers";

    String inputUserSelectedMenuNumber_GuideMessage="[1] CREATE_VOUCHER \n[2] SHOW ALL VOUCHER LIST \n" +
                                                     "[3] CREATE ORDER \n[4] SHOW ALL ORDER LIST \n[5] CREATE COLLECTION " +
                                                    "\n[6] CUSTOMER BLACK LIST \n[7] EXIT ";

    String inputUserSelectedVoucherType_GuideMessage ="[1] Fixed Amount Type \n[2] Percent Amount Type";
    String inputUserSelectedVoucherType = "Amount Type : ";
    String showVoucherList_GuideMessage = "Show Voucher List ....................";
    String showAllBlackList_GuideMessage = "Show All Black LIst ....................";
    String success_Amount_Save ="Saving Succeeded, Created Voucher Id: ";
    String inputAmount_GuideMessage="Please Put The Amount To Save";
    String inputAmount="Amount : ";


}
