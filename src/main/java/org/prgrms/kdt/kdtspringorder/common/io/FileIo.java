package org.prgrms.kdt.kdtspringorder.common.io;

import java.io.IOException;
import java.util.List;

public interface FileIo<E> {

    /**
     * 파일 쓰기
     * @param oList 파일에 쓸 데이터 리스트
     * @throws IOException
     */
    public void write(List<E> oList);

    /**
     * 파일 읽기
     * @return 읽은 파일의 데이터들을 리스트 형태로 반환합니다.
     * @throws IOException
     */
    public List<E> readAllLines();

}
