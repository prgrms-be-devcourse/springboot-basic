package org.prgrms.deukyun.voucherapp.app.menu;

/**
 * 메뉴
 * @param <E> - 부모 메뉴의 선택
 */
public abstract class Menu<E extends Enum<E>> {

    /**
     * 메뉴는 자신을 선택하기 위한 enum 타입을 생성시 세팅하여 가지고 있는다.
     */
    private final E choice;

    protected Menu(E choice) {
        this.choice = choice;
    }

    public E getChoice() {
        return choice;
    }

    /**
     * 로직 실행 <br>
     * 메뉴의 직접적인 로직과 관련없는 선택에 관련된 출력은 최대한 각 Choice enum에 보관하도록 함
     */
    public abstract void proc();

}
