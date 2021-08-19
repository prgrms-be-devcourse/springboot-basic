package org.prgrms.kdt.file;

/**
 * Created by yhh1056
 * Date: 2021/08/19 Time: 10:53 오전
 *
 * T : 반환 할 타입
 * E : 저장 할 객체
 */
public interface FileRepository<E> {

    void saveFile(E e);

}
