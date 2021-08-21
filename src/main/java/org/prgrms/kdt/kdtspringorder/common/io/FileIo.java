package org.prgrms.kdt.kdtspringorder.common.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileIo {

    /**
     * 파일 쓰기
     * @param strList 파일에 쓸 문자열 리스트
     * @throws IOException
     */
    public void write(List<String> strList) throws IOException;

    /**
     * 파일 읽기
     * @return 읽은 파일의 문자열들을 리스트 형태로 반환합니다.
     * @throws IOException
     */
    public List<String> readAllLines() throws IOException;

}
