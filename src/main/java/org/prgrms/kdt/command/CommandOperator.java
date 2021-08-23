package org.prgrms.kdt.command;

import java.util.Map;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 3:22 오후
 *
 * K : 데이터의 키 값
 * E : 데이터
 * V : 사용자가 입력한 값
 */
public interface CommandOperator<K, E, V> {

    E create(V v);

    Map<K, E> getAll();

    void exit();
}
