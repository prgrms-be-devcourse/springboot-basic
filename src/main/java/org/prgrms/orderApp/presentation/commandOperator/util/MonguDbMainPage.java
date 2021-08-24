package org.prgrms.orderApp.presentation.commandOperator.util;

public enum MonguDbMainPage {
    //CONNECT(1, "COLLECTION_CONNECT"),
    CREATE(1,"COLLECTION_CREATE"),
    DROP(2,"COLLECTION_DROP");



    private int menuNumber;
    private String menuName;

    MonguDbMainPage(int menuNumber, String menuName){
        this.menuNumber = menuNumber;
        this.menuName = menuName;
    }

    public String getMenuName(){
        return menuName;
    }

    public static String getMenuName(int menuNumber){
        if(menuNumber== MonguDbMainPage.CREATE.menuNumber){
            return MonguDbMainPage.CREATE.menuName;
        }else if(menuNumber== MonguDbMainPage.DROP.menuNumber){
            return MonguDbMainPage.DROP.menuName;
        }else{
            return "";
        }
    }
}
