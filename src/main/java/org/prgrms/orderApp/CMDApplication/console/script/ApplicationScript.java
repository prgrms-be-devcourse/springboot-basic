package org.prgrms.orderApp.CMDApplication.console.script;


public interface ApplicationScript {
    final String startMessage = "=== Voucher Program === \nType exit to exit the program\n" +
                                        "Type created to a new voucher\nType list to list all vouchers";
    final String guideMessage = "Please Select The Number To Want To Do \n";
    final String inputUserSelectedMenuNumber_GuideMessage="[1] CREATE_VOUCHER \n[2] SHOW ALL VOUCHER LIST \n" +
                                                     "[3] CREATE ORDER \n[4] SHOW ALL ORDER LIST \n[5] CONNECT FILE DB \n[6] EXIT ";
    final String inputUserSelectedMenuNumber = "Selected Menu(number) : ";
    final String inputAmount_GuideMessage="Please Put The Amount To Save";
    final String inputAmount="Amount : ";
    final String inputUserSelectedVoucherType_GuideMessage ="[1] Fixed Amount Type \n[2] Percent Amount Type";
    final String inputUserSelectedVoucherType = "Amount Type : ";
    final String showVoucherList_GuideMessage = "Show Voucher List ....................";
    final String success_Amount_Save ="Saving Succeeded, Created Voucher Id: ";
    final String emptyData ="No Result";


}
